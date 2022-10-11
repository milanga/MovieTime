package com.movietime.movie.detail.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.movietime.core.presentation.ListState
import com.movietime.core.views.model.PosterItem
import com.movietime.movie.detail.presentation.model.MovieDetailUiState
import com.movietime.movie.detail.presentation.model.toPosterItem
import com.movietime.movie.detail.presentation.model.toUiMovieDetail
import com.movietime.movie.detail.presentation.model.toUiVideo
import com.movietime.movie.domain.interactors.GetMovieDetailUseCase
import com.movietime.movie.domain.interactors.GetMovieRecommendationsUseCase
import com.movietime.movie.domain.interactors.GetMovieVideosUseCase
import com.movietime.movie.domain.model.MovieDetail
import com.movietime.movie.domain.model.MoviePreview
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
                    .catch { it.printStackTrace() }
                    .map { recommendationPage ->
                        recommendationPage.map(MoviePreview::toPosterItem)
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

    // UI state exposed to the UI
    val uiState: StateFlow<MovieDetailUiState> = combine(
        getMovieDetailUseCase(movieId).map(MovieDetail::toUiMovieDetail),
        getMovieVideosUseCase(movieId).map { it.map(Video::toUiVideo) },
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