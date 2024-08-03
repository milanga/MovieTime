package com.movietime.movie.detail.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.movietime.core.presentation.ListState
import com.movietime.domain.interactors.movie.AddMovieToWatchlistUseCase
import com.movietime.domain.interactors.movie.GetMovieDetailUseCase
import com.movietime.domain.interactors.movie.GetMovieRecommendationsUseCase
import com.movietime.domain.interactors.movie.GetMovieVideosUseCase
import com.movietime.domain.interactors.movie.IsMovieInWatchlistUseCase
import com.movietime.domain.interactors.movie.ToggleMovieFromWatchlistUseCase
import com.movietime.domain.model.MovieDetail
import com.movietime.domain.model.MoviePreview
import com.movietime.domain.model.Video
import com.movietime.movie.detail.presentation.model.MovieDetailUiState
import com.movietime.movie.detail.presentation.model.toPosterItem
import com.movietime.movie.detail.presentation.model.toUiMovieDetail
import com.movietime.movie.detail.presentation.model.toUiVideo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getMovieRecommendationsUseCase: GetMovieRecommendationsUseCase,
    private val getMovieDetailUseCase: GetMovieDetailUseCase,
    private val getMovieVideosUseCase: GetMovieVideosUseCase,
    private val toggleMovieFromWatchlistUseCase: ToggleMovieFromWatchlistUseCase,
    private val isMovieInWatchlistUseCase: IsMovieInWatchlistUseCase
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
        recommendationsListState.refresh()
        combine(
            getMovieDetailUseCase(movieId).map(MovieDetail::toUiMovieDetail),
            getMovieVideosUseCase(movieId).map { it.map(Video::toUiVideo) },
            getMovieRecommendationsUseCase.recommendedMovies.map{ it.map(MoviePreview::toPosterItem) },
            isMovieInWatchlistUseCase(movieId)
        ) { movieDetail, videos, recommendations, isMovieInWatchlist ->
            val movieDetailUiState: MovieDetailUiState = MovieDetailUiState.Content(
                movieDetail,
                videos,
                recommendations,
                isMovieInWatchlist
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

    fun onRecommendationsThreshold() {
        recommendationsListState.thresholdReached()
    }

    fun toggleWatchlist() {
        viewModelScope.launch {
            try {
                toggleMovieFromWatchlistUseCase(movieId)
            } catch (e: Exception) {
                e.printStackTrace()
                //todo handle error
            }
        }
    }

}