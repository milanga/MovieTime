package com.movietime.tvshow.detail.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.movietime.core.presentation.ListState
import com.movietime.domain.interactors.auth.GetAuthorizationRedirectUri
import com.movietime.domain.interactors.movie.AddTvShowToWatchlistUseCase
import com.movietime.domain.interactors.tvshow.GetTvShowDetailUseCaseFactory
import com.movietime.domain.interactors.tvshow.GetTvShowRecommendationsUseCaseFactory
import com.movietime.domain.interactors.tvshow.GetTvShowVideosUseCaseFactory
import com.movietime.domain.interactors.tvshow.TvShowDetailRepositoryFactory
import com.movietime.domain.model.TvShowDetail
import com.movietime.domain.model.TvShowPreview
import com.movietime.domain.model.Video
import com.movietime.tvshow.detail.presentation.model.TvShowDetailUiState
import com.movietime.tvshow.detail.presentation.model.toPosterItem
import com.movietime.tvshow.detail.presentation.model.toUiTvShowDetail
import com.movietime.tvshow.detail.presentation.model.toUiVideo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TvShowDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getTvShowRecommendationsUseCaseFactory: GetTvShowRecommendationsUseCaseFactory,
    getTvShowDetailUseCaseFactory: GetTvShowDetailUseCaseFactory,
    getTvShowVideosUseCaseFactory: GetTvShowVideosUseCaseFactory,
    movieDetailRepositoryFactory: TvShowDetailRepositoryFactory,
    private val addTvShowToWatchlistUseCase: AddTvShowToWatchlistUseCase
) : ViewModel() {
    private val tvShowDetailId: Int = savedStateHandle["paramTvShowId"]!!
    private val repository = movieDetailRepositoryFactory.create(tvShowDetailId)
    private val getTvShowDetailUseCase = getTvShowDetailUseCaseFactory.create(repository)
    private val getTvShowRecommendationsUseCase = getTvShowRecommendationsUseCaseFactory.create(repository)
    private val getTvShowVideosUseCase = getTvShowVideosUseCaseFactory.create(repository)

    private val recommendationsListState = ListState({
        fetchRecommendations { getTvShowRecommendationsUseCase.refresh() }
    },{
        fetchRecommendations { getTvShowRecommendationsUseCase.fetchMore() }
    })

    private fun fetchRecommendations(fetchFunction: suspend ()->Unit){
        viewModelScope.launch {
            try {
                fetchFunction()
            } catch (e: Exception) {
                e.printStackTrace()
                //todo handle error
            } finally {
                recommendationsListState.finishLoading()
            }
        }
    }

    // UI state exposed to the UI
    val uiState: StateFlow<TvShowDetailUiState> by lazy {
        initializeData()
        combine(
            getTvShowDetailUseCase.tvShowDetail.map(TvShowDetail::toUiTvShowDetail),
            getTvShowVideosUseCase.tvShowVideos.map { it.map(Video::toUiVideo) },
            getTvShowRecommendationsUseCase.recommendedTvShows.map{ it.map(TvShowPreview::toPosterItem) },
        ) { tvShowDetail, videos, recommendations ->
            val tvShowDetailUiState: TvShowDetailUiState = TvShowDetailUiState.Content(
                tvShowDetail,
                videos,
                recommendations
            )
            tvShowDetailUiState
        }.catch { throwable ->
            throwable.printStackTrace()
            emit(TvShowDetailUiState.Error)
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            TvShowDetailUiState.Loading
        )
    }

    private fun initializeData() {
        recommendationsListState.refresh()
        viewModelScope.launch {
            getTvShowDetailUseCase.fetchTvShowDetail()
            getTvShowVideosUseCase.fetchTvShowVideos()
        }
    }

    fun onRecommendationsThreshold() {
        recommendationsListState.thresholdReached()
    }

    fun addToWatchList() {
        viewModelScope.launch {
            addTvShowToWatchlistUseCase(tvShowDetailId)
        }
    }

}