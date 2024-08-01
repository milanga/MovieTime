package com.movietime.tvshow.detail.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.movietime.core.presentation.ListState
import com.movietime.domain.interactors.tvshow.AddTvShowToWatchlistUseCase
import com.movietime.domain.interactors.tvshow.GetTvShowDetailUseCase
import com.movietime.domain.interactors.tvshow.GetTvShowRecommendationsUseCase
import com.movietime.domain.interactors.tvshow.GetTvShowVideosUseCase
import com.movietime.domain.model.TvShowDetail
import com.movietime.domain.model.TvShowPreview
import com.movietime.domain.model.Video
import com.movietime.tvshow.detail.presentation.model.TvShowDetailUiState
import com.movietime.tvshow.detail.presentation.model.toPosterItem
import com.movietime.tvshow.detail.presentation.model.toUiTvShowDetail
import com.movietime.tvshow.detail.presentation.model.toUiVideo
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
class TvShowDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getTvShowRecommendationsUseCase: GetTvShowRecommendationsUseCase,
    private val getTvShowDetailUseCase: GetTvShowDetailUseCase,
    private val getTvShowVideosUseCase: GetTvShowVideosUseCase,
    private val addTvShowToWatchlistUseCase: AddTvShowToWatchlistUseCase
) : ViewModel() {
    private val tvShowDetailId: Int = savedStateHandle["paramTvShowId"]!!

    private val recommendationsListState = ListState({
        fetchRecommendations { getTvShowRecommendationsUseCase.refresh(tvShowDetailId) }
    },{
        fetchRecommendations { getTvShowRecommendationsUseCase.fetchMore(tvShowDetailId) }
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
        recommendationsListState.refresh()
        combine(
            getTvShowDetailUseCase(tvShowDetailId).map(TvShowDetail::toUiTvShowDetail),
            getTvShowVideosUseCase(tvShowDetailId).map { it.map(Video::toUiVideo) },
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

    fun onRecommendationsThreshold() {
        recommendationsListState.thresholdReached()
    }

    fun addToWatchList() {
        viewModelScope.launch {
            addTvShowToWatchlistUseCase(tvShowDetailId)
        }
    }

}