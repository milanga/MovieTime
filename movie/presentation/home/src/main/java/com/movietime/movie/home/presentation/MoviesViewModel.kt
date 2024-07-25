package com.movietime.movie.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.movietime.core.presentation.ListState
import com.movietime.core.views.poster.model.PosterItem
import com.movietime.movie.detail.presentation.model.toPosterItem
import com.movietime.domain.interactors.movie.GetPopularMoviesUseCase
import com.movietime.domain.interactors.movie.GetTopRatedMoviesUseCase
import com.movietime.domain.interactors.movie.GetUpcomingMoviesUseCase
import com.movietime.domain.model.MoviePreview
import com.movietime.core.views.highlight.model.HighlightedItem
import com.movietime.movie.detail.presentation.model.toHighlightedItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    private val getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase
): ViewModel() {
    sealed interface MoviesUiState{
        object Loading: MoviesUiState
        object Error: MoviesUiState
        data class Content(
            val popularMovies: List<HighlightedItem>,
            val topRatedMovies: List<PosterItem>,
            val upcomingMovies: List<PosterItem>
        ): MoviesUiState
    }

    private val popularListState = ListState({
        fetchList(this) { getPopularMoviesUseCase.refresh() }
    },{
        fetchList(this) { getPopularMoviesUseCase.fetchMore() }
    })

    private val topRatedListState = ListState({
        fetchList(this) { getTopRatedMoviesUseCase.refresh() }
    },{
        fetchList(this) { getTopRatedMoviesUseCase.fetchMore() }
    })

    private val upcomingListState = ListState({
        fetchList(this) { getUpcomingMoviesUseCase.refresh() }
    },{
        fetchList(this) { getUpcomingMoviesUseCase.fetchMore() }
    })

    private fun fetchList(listState: ListState, fetchFunction: suspend ()->Unit){
        viewModelScope.launch {
            try {
                fetchFunction()
            } catch (e: Exception) {
                //todo handle error
            } finally {
                listState.finishLoading()
            }
        }
    }

    val uiState: StateFlow<MoviesUiState> by lazy {
        popularListState.refresh()
        topRatedListState.refresh()
        upcomingListState.refresh()
        combine(
            getPopularMoviesUseCase.popularMovies.map { it.map(MoviePreview::toHighlightedItem) },
            getTopRatedMoviesUseCase.topRatedMovies.map { it.map(MoviePreview::toPosterItem) },
            getUpcomingMoviesUseCase.upcomingMovies.map { it.map(MoviePreview::toPosterItem) }
        ) { popularMovies, topRatedMovies, upcomingMovies ->
            if(popularMovies.isEmpty() || topRatedMovies.isEmpty() || upcomingMovies.isEmpty()){
                MoviesUiState.Loading
            } else {
                val movieDetailUiState: MoviesUiState = MoviesUiState.Content(
                    popularMovies,
                    topRatedMovies,
                    upcomingMovies
                )
                movieDetailUiState
            }
        }.catch { throwable ->
            throwable.printStackTrace()
            emit(MoviesUiState.Error)
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            MoviesUiState.Loading
        )
    }

    fun onTopRatedMoviesThreshold(){
        topRatedListState.thresholdReached()
    }

    fun onUpcomingMoviesThreshold(){
        upcomingListState.thresholdReached()
    }

    fun onPopularMoviesThreshold(){
        popularListState.thresholdReached()
    }
}