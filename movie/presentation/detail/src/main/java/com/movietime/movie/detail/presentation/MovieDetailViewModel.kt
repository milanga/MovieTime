package com.movietime.movie.detail.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.movietime.core.presentation.ListState
import com.movietime.core.views.model.PosterItem
import com.movietime.movie.domain.interactors.GetMovieDetailUseCase
import com.movietime.movie.domain.interactors.GetMovieRecommendationsUseCase
import com.movietime.movie.domain.interactors.GetMovieVideosUseCase
import com.movietime.movie.domain.model.MovieDetail
import com.movietime.movie.domain.model.Video
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getMovieRecommendationsUseCase: GetMovieRecommendationsUseCase,
    getMovieDetailUseCase: GetMovieDetailUseCase,
    getMovieVideosUseCase: GetMovieVideosUseCase
) : ViewModel() {
    private val movieId: Int = savedStateHandle["paramMovieId"]!!

    private val recommendationsListState = ListState().apply {
        onLoadPage = { page ->
            viewModelScope.launch {
                getMovieRecommendationsUseCase(movieId, page)
                    .onCompletion { this@apply.finishLoading() }
                    .map { recommendationPage ->
                        recommendationPage.map { PosterItem(it.id, it.posterPath, "%.1f".format(it.rating)) }
                    }
                    .collectLatest { recommendationItems ->
                        recommendations.getAndUpdate{ currentList ->
                            currentList + recommendationItems
                        }
                    }
            }
        }
    }

    private val recommendations: MutableStateFlow<List<PosterItem>> by lazy {
        recommendationsListState.refresh()
        MutableStateFlow(emptyList())
    }

    sealed interface MovieDetailUiState {
        object Error : MovieDetailUiState
        object Loading : MovieDetailUiState
        data class Content(
            val movieDetail: MovieDetail,
            val movieVideos: List<Video>,
            val movieRecommendations: List<PosterItem>
        ) : MovieDetailUiState
    }

    // UI state exposed to the UI
    val uiState: StateFlow<MovieDetailUiState> = combine(
        getMovieDetailUseCase(movieId),
        getMovieVideosUseCase(movieId),
        recommendations
    ) { movieDetail, videos, recommendations ->
        val movieDetailUiState: MovieDetailUiState = MovieDetailUiState.Content(
            movieDetail,
            videos,
            recommendations
        )
        movieDetailUiState
    }.catch { throwable ->
        throwable.printStackTrace()
        emit(MovieDetailUiState.Error)
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        MovieDetailUiState.Loading
    )

    fun onRecommendationsThreshold() {
        recommendationsListState.thresholdReached()
    }

}