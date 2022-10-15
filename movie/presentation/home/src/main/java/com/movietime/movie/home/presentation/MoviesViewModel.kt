package com.movietime.movie.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.movietime.core.presentation.ListState
import com.movietime.core.views.model.PosterItem
import com.movietime.movie.detail.presentation.model.toPosterItem
import com.movietime.movie.domain.interactors.PopularMoviesUseCase
import com.movietime.movie.domain.interactors.TopRatedMoviesUseCase
import com.movietime.movie.domain.interactors.UpcomingMoviesUseCase
import com.movietime.movie.domain.model.MoviePreview
import com.movietime.movie.home.presentation.model.HighlightedItem
import com.movietime.movie.home.presentation.model.toHighlightedItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val popularMoviesUseCase: PopularMoviesUseCase,
    private val topRatedMoviesUseCase: TopRatedMoviesUseCase,
    private val upcomingMoviesUseCase: UpcomingMoviesUseCase
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

    private val popularListState = ListState { page ->
        loadPage(this){popularMoviesUseCase.loadPage(page)}
    }

    private val topRatedListState = ListState { page ->
        loadPage(this){topRatedMoviesUseCase.loadPage(page)}
    }

    private val upcomingListState = ListState { page ->
        loadPage(this){upcomingMoviesUseCase.loadPage(page)}
    }

    private fun loadPage(state: ListState, pageLoader: suspend ()->Unit){
        viewModelScope.launch {
            try {
                pageLoader()
            }catch(e: Exception){
                //todo handle error
            } finally{
                state.finishLoading()
            }
        }
    }

    val uiState: StateFlow<MoviesUiState> by lazy {
        popularListState.refresh()
        topRatedListState.refresh()
        upcomingListState.refresh()
        combine(
            popularMoviesUseCase.popularMovies.map { it.map(MoviePreview::toHighlightedItem) },
            topRatedMoviesUseCase.topRatedMovies.map { it.map(MoviePreview::toPosterItem) },
            upcomingMoviesUseCase.upcomingMovies.map { it.map(MoviePreview::toPosterItem) }
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