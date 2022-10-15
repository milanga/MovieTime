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
    private val getMovieDetailUseCase: GetMovieDetailUseCase,
    private val getMovieVideosUseCase: GetMovieVideosUseCase
) : ViewModel() {
    private val movieId: Int = savedStateHandle["paramMovieId"]!!

    private val recommendationsListState = ListState({
        fetchRecommendations { getMovieRecommendationsUseCase.refresh(movieId) }
    },{
        fetchRecommendations { getMovieRecommendationsUseCase.fetchMore(movieId) }
    })

    private fun fetchRecommendations(fetchFunction: suspend ()->Unit){
        viewModelScope.launch {
            try {
                fetchFunction()
            } catch (e: Exception) {
                //todo handle error
            } finally {
                recommendationsListState.finishLoading()
            }
        }
    }

    // UI state exposed to the UI
    val uiState: StateFlow<MovieDetailUiState> by lazy {
        initializeData()
        combine(
            getMovieDetailUseCase.movieDetail.map(MovieDetail::toUiMovieDetail),
            getMovieVideosUseCase.movieVideos.map { it.map(Video::toUiVideo) },
            getMovieRecommendationsUseCase.recommendedMovies.map{ it.map(MoviePreview::toPosterItem) }
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
    }

    private fun initializeData() {
        recommendationsListState.refresh()
        viewModelScope.launch {
            getMovieDetailUseCase.fetchMovieDetail(movieId)
            getMovieVideosUseCase.fetchMovieVideos(movieId)
        }
    }

    fun onRecommendationsThreshold() {
        recommendationsListState.thresholdReached()
    }

}