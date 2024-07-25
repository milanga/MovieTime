package com.movietime.movie.detail.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.movietime.core.presentation.ListState
import com.movietime.movie.detail.presentation.model.MovieDetailUiState
import com.movietime.movie.detail.presentation.model.toPosterItem
import com.movietime.movie.detail.presentation.model.toUiMovieDetail
import com.movietime.movie.detail.presentation.model.toUiVideo
import com.movietime.domain.interactors.GetMovieDetailUseCaseFactory
import com.movietime.domain.interactors.GetMovieRecommendationsUseCaseFactory
import com.movietime.domain.interactors.GetMovieVideosUseCaseFactory
import com.movietime.domain.interactors.MovieDetailRepositoryFactory
import com.movietime.domain.model.MovieDetail
import com.movietime.domain.model.MoviePreview
import com.movietime.domain.model.Video
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getMovieRecommendationsUseCaseFactory: GetMovieRecommendationsUseCaseFactory,
    getMovieDetailUseCaseFactory: GetMovieDetailUseCaseFactory,
    getMovieVideosUseCaseFactory: GetMovieVideosUseCaseFactory,
    movieDetailRepositoryFactory: MovieDetailRepositoryFactory
) : ViewModel() {
    private val repository = movieDetailRepositoryFactory.create(savedStateHandle["paramMovieId"]!!)
    private val getMovieDetailUseCase = getMovieDetailUseCaseFactory.create(repository)
    private val getMovieRecommendationsUseCase = getMovieRecommendationsUseCaseFactory.create(repository)
    private val getMovieVideosUseCase = getMovieVideosUseCaseFactory.create(repository)

    private val recommendationsListState = ListState({
        fetchRecommendations { getMovieRecommendationsUseCase.refresh() }
    },{
        fetchRecommendations { getMovieRecommendationsUseCase.fetchMore() }
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
            getMovieDetailUseCase.fetchMovieDetail()
            getMovieVideosUseCase.fetchMovieVideos()
        }
    }

    fun onRecommendationsThreshold() {
        recommendationsListState.thresholdReached()
    }

}