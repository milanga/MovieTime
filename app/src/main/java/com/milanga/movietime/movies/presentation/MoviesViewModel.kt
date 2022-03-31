package com.milanga.movietime.movies.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.milanga.movietime.core.ListState
import com.milanga.movietime.core.UIContentState
import com.milanga.movietime.core.ViewModelContentState
import com.milanga.movietime.movies.domain.MoviePreview
import com.milanga.movietime.movies.interactors.GetPopularMoviesUseCase
import com.milanga.movietime.movies.interactors.GetTopRatedMoviesUseCase
import com.milanga.movietime.movies.interactors.GetUpcomingMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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
        object Error: MoviesUiState
        data class Content(
            val popularMovies: UIContentState<List<MoviePreview>> = UIContentState.Loading(),
            val topRatedMovies: UIContentState<List<MoviePreview>> = UIContentState.Loading(),
            val upcomingMovies: UIContentState<List<MoviePreview>> = UIContentState.Loading()
        ): MoviesUiState
    }

    private data class MoviesViewModelState(
        val popularMovies: ViewModelContentState<List<MoviePreview>>,
        val topRatedMovies: ViewModelContentState<List<MoviePreview>>,
        val upcomingMovies: ViewModelContentState<List<MoviePreview>>
    ){
        fun toUiState(): MoviesUiState {
            if (
                popularMovies is ViewModelContentState.Error ||
                topRatedMovies is ViewModelContentState.Error ||
                upcomingMovies is ViewModelContentState.Error
            ){
                return MoviesUiState.Error
            } else {
                return MoviesUiState.Content(
                    popularMovies.toUiContentState(),
                    topRatedMovies.toUiContentState(),
                    upcomingMovies.toUiContentState()
                )
            }
        }
    }

    private val viewModelState = MutableStateFlow(
        MoviesViewModelState(
        ViewModelContentState.Loading(),
        ViewModelContentState.Loading(),
        ViewModelContentState.Loading()
    )
    )


    // UI state exposed to the UI
    val uiState by lazy {
        loadMoviesSections()
        return@lazy viewModelState
            .map { it.toUiState() }
            .stateIn(
                viewModelScope,
                SharingStarted.Lazily,
                viewModelState.value.toUiState()
            )
    }

    private val topRatedListState: ListState = ListState().apply {
        onLoadPage = { page ->
            loadMovies(
                { getTopRatedMoviesUseCase(page) },
                { movieList, state ->
                    val movies = if (state.topRatedMovies is ViewModelContentState.ContentState){
                        state.topRatedMovies.content.plus(movieList)
                    } else {
                        movieList
                    }
                    state.copy(topRatedMovies = ViewModelContentState.ContentState(movies))
                },
                { state ->
                    state.copy(topRatedMovies = ViewModelContentState.Error("Error loading top rated movies"))
                },
                {
                    finishLoading()
                }
            )
        }
    }

    private val upcomingListState: ListState = ListState().apply {
        onLoadPage = { page ->
            loadMovies(
                { getUpcomingMoviesUseCase(page) },
                { movieList, state ->
                    val movies = if (state.upcomingMovies is ViewModelContentState.ContentState){
                        state.upcomingMovies.content.plus(movieList)
                    } else {
                        movieList
                    }
                    state.copy(upcomingMovies = ViewModelContentState.ContentState(movies))
                },
                { state ->
                    state.copy(upcomingMovies = ViewModelContentState.Error("Error loading upcoming movies"))
                },
                {
                    finishLoading()
                }
            )
        }
    }

    private val popularListState: ListState = ListState().apply {
        onLoadPage = { page ->
            loadMovies(
                { getPopularMoviesUseCase(page) },
                { movieList, state ->
                    val movies = if (state.popularMovies is ViewModelContentState.ContentState){
                        state.popularMovies.content.plus(movieList)
                    } else {
                        movieList
                    }
                    state.copy(popularMovies = ViewModelContentState.ContentState(movies))
                },
                { state ->
                    state.copy(popularMovies = ViewModelContentState.Error("Error loading popular movies"))
                },
                {
                    finishLoading()
                }
            )
        }
    }

    private fun loadMoviesSections() {
        popularListState.refresh()
        topRatedListState.refresh()
        upcomingListState.refresh()
    }

    private fun loadMovies(
        useCaseCall: () -> Flow<List<MoviePreview>>,
        onSuccess: (List<MoviePreview>, MoviesViewModelState) -> MoviesViewModelState,
        onError: (MoviesViewModelState) -> MoviesViewModelState,
        onComplete: ()->Unit = {}
    ) {
        viewModelScope.launch {
            useCaseCall()
                .flowOn(Dispatchers.Default)
                .catch {
                    it.printStackTrace()
                    viewModelState.update {
                        onError(it)
                    }
                }.onCompletion {
                    onComplete()
                }
                .collect { movieList ->
                    viewModelState.update{
                        onSuccess(movieList, it)
                    }
                }
        }
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