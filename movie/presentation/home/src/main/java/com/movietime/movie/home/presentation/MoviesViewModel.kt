package com.movietime.movie.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.movietime.core.presentation.ListState
import com.movietime.core.views.model.PosterItem
import com.movietime.movie.detail.presentation.model.toPosterItem
import com.movietime.movie.domain.interactors.GetPopularMoviesUseCase
import com.movietime.movie.domain.interactors.GetTopRatedMoviesUseCase
import com.movietime.movie.domain.interactors.GetUpcomingMoviesUseCase
import com.movietime.movie.domain.model.MoviePreview
import com.movietime.movie.home.presentation.model.HighlightedItem
import com.movietime.movie.home.presentation.model.toHighlightedItem
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

    private val popularListState = ListState().apply {
        onLoadPage = { page ->
            viewModelScope.launch {
                getPopularMoviesUseCase(page)
                    .onCompletion { this@apply.finishLoading() }
                    .catch { it.printStackTrace() }
                    .map { popularMovies ->
                        popularMovies.map(MoviePreview::toHighlightedItem)
                    }
                    .collectLatest { popularItems ->
                        popularMovies.getAndUpdate{ currentList ->
                            currentList + popularItems
                        }
                    }
            }
        }
    }

    private val popularMovies: MutableStateFlow<List<HighlightedItem>> by lazy {
        popularListState.refresh()
        MutableStateFlow(emptyList())
    }

    private val topRatedListState = ListState().apply {
        onLoadPage = { page ->
            viewModelScope.launch {
                getTopRatedMoviesUseCase(page)
                    .onCompletion { this@apply.finishLoading() }
                    .catch { it.printStackTrace() }
                    .map { topRatedPage ->
                        topRatedPage.map(MoviePreview::toPosterItem)
                    }
                    .collectLatest { topRateItems ->
                        topRatedMovies.getAndUpdate{ currentList ->
                            currentList + topRateItems
                        }
                    }
            }
        }
    }

    private val topRatedMovies: MutableStateFlow<List<PosterItem>> by lazy {
        topRatedListState.refresh()
        MutableStateFlow(emptyList())
    }

    private val upcomingListState = ListState().apply {
        onLoadPage = { page ->
            viewModelScope.launch {
                getUpcomingMoviesUseCase(page)
                    .onCompletion { this@apply.finishLoading() }
                    .catch { it.printStackTrace() }
                    .map { upcomingPage ->
                        upcomingPage.map(MoviePreview::toPosterItem)
                    }
                    .collectLatest { upcomingItems ->
                        upcomingMovies.getAndUpdate{ currentList ->
                            currentList + upcomingItems
                        }
                    }
            }
        }
    }

    private val upcomingMovies: MutableStateFlow<List<PosterItem>> by lazy {
        upcomingListState.refresh()
        MutableStateFlow(emptyList())
    }

    val uiState: StateFlow<MoviesUiState> = combine(
        popularMovies,
        topRatedMovies,
        upcomingMovies
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