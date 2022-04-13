package com.movietime.movie.detail.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.movietime.core.domain.ListState
import com.movietime.core.domain.UIContentState
import com.movietime.core.domain.ViewModelContentState
import com.movietime.movie.interactors.GetMovieDetailUseCase
import com.movietime.movie.interactors.GetMovieRecommendationsUseCase
import com.movietime.movie.interactors.GetMovieVideosUseCase
import com.movietime.movie.domain.detail.MovieDetail
import com.movietime.movie.domain.detail.Video
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getMovieRecommendationsUseCase: GetMovieRecommendationsUseCase,
    private val getMovieDetailUseCase: GetMovieDetailUseCase,
    private val getMovieVideosUseCase: GetMovieVideosUseCase
) : ViewModel() {
    private val movieId: Int = savedStateHandle.get("paramMovieId")!!

    sealed interface MovieDetailUiState {
        object Error : MovieDetailUiState
        data class Content(
            val movieDetail: UIContentState<MovieDetail> = UIContentState.Loading(),
            val movieVideos: UIContentState<List<Video>> = UIContentState.Loading(),
            val movieRecommendations: UIContentState<List<com.movietime.movie.domain.MoviePreview>> = UIContentState.Loading()
        ) : MovieDetailUiState
    }

    private data class MovieDetailViewModelState(
        val movieDetail: ViewModelContentState<MovieDetail>,
        val movieVideos: ViewModelContentState<List<Video>>,
        val movieRecommendations: ViewModelContentState<List<com.movietime.movie.domain.MoviePreview>>
    ) {
        fun toUiState(): MovieDetailUiState {
            if (
                movieDetail is ViewModelContentState.Error ||
                movieVideos is ViewModelContentState.Error ||
                movieRecommendations is ViewModelContentState.Error
            ) {
                return MovieDetailUiState.Error
            } else {
                return MovieDetailUiState.Content(
                    movieDetail.toUiContentState(),
                    movieVideos.toUiContentState(),
                    movieRecommendations.toUiContentState()
                )
            }
        }
    }

    private val viewModelState = MutableStateFlow(
        MovieDetailViewModelState(
            ViewModelContentState.Loading(),
            ViewModelContentState.Loading(),
            ViewModelContentState.Loading()
        )
    )


    // UI state exposed to the UI
    val uiState by lazy {
        loadMovieDetail()
        loadMovieVideos()
        recommendationsListState.refresh()
        return@lazy viewModelState
            .map { it.toUiState() }
            .stateIn(
                viewModelScope,
                SharingStarted.Lazily,
                viewModelState.value.toUiState()
            )
    }

    private fun loadMovieDetail() {
        viewModelScope.launch {
            getMovieDetailUseCase(movieId)
                .flowOn(Dispatchers.Default)
                .catch { flowCollector ->
                    flowCollector.printStackTrace()
                    viewModelState.update { state ->
                        state.copy(movieDetail = ViewModelContentState.Error("Error loading movie detail"))
                    }
                }
                .collect { movieDetail ->
                    viewModelState.update { state ->
                        state.copy(movieDetail = ViewModelContentState.ContentState(movieDetail))
                    }
                }
        }
    }

    private fun loadMovieVideos() {
        viewModelScope.launch {
            getMovieVideosUseCase(movieId)
                .flowOn(Dispatchers.Default)
                .catch { flowCollector ->
                    flowCollector.printStackTrace()
                    viewModelState.update { state ->
                        state.copy(movieVideos = ViewModelContentState.Error("Error loading movie videos"))
                    }
                }
                .collect { movieVideos ->
                    viewModelState.update { state ->
                        state.copy(movieVideos = ViewModelContentState.ContentState(movieVideos))
                    }
                }
        }
    }


    private val recommendationsListState = ListState().apply {
        onLoadPage = { _ ->
            viewModelScope.launch {
                getMovieRecommendationsUseCase(movieId)
                    .flowOn(Dispatchers.Default)
                    .catch { flowCollector ->
                        flowCollector.printStackTrace()
                        viewModelState.update { state ->
                            state.copy(movieRecommendations = ViewModelContentState.Error("Error loading movie recommendations"))
                        }
                    }
                    .collect { movieList ->
                        viewModelState.update { state ->
                            val movies =
                                if (state.movieRecommendations is ViewModelContentState.ContentState) {
                                    state.movieRecommendations.content.plus(movieList)
                                } else {
                                    movieList
                                }
                            state.copy(
                                movieRecommendations = ViewModelContentState.ContentState(
                                    movies
                                )
                            )
                        }
                    }
            }
        }
    }


    fun onRecommendationsThreshold() {
        recommendationsListState.thresholdReached()
    }

}