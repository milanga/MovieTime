package com.movietime.tvshow.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.movietime.core.presentation.ListState
import com.movietime.core.views.poster.model.PosterItem
import com.movietime.core.views.highlight.model.HighlightedItem
import com.movietime.domain.interactors.tvshow.GetTvShowsWatchlistUseCase
import com.movietime.domain.interactors.tvshow.GetOnTheAirTvShowsUseCase
import com.movietime.domain.interactors.tvshow.GetPopularTvShowsUseCase
import com.movietime.domain.interactors.tvshow.GetTopRatedTvShowsUseCase
import com.movietime.domain.model.TvShowDetail
import com.movietime.domain.model.TvShowPreview
import com.movietime.tvshow.detail.presentation.model.toHighlightedItem
import com.movietime.tvshow.detail.presentation.model.toPosterItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TvShowsViewModel @Inject constructor(
    private val getPopularTvShowsUseCase: GetPopularTvShowsUseCase,
    private val getTopRatedTvShowsUseCase: GetTopRatedTvShowsUseCase,
    private val getOnTheAirTvShowsUseCase: GetOnTheAirTvShowsUseCase,
    private val getWatchlistTvShowsUseCase: GetTvShowsWatchlistUseCase
): ViewModel() {
    sealed interface TvShowsUiState{
        object Loading: TvShowsUiState
        object Error: TvShowsUiState
        data class Content(
            val popularTvShows: List<HighlightedItem>,
            val topRatedTvShows: List<PosterItem>,
            val onTheAirTvShows: List<PosterItem>,
            val watchlistTvShows: List<HighlightedItem>
        ): TvShowsUiState
    }

    private val popularListState = ListState({
        fetchList(this) { getPopularTvShowsUseCase.refresh() }
    },{
        fetchList(this) { getPopularTvShowsUseCase.fetchMore() }
    })

    private val topRatedListState = ListState({
        fetchList(this) { getTopRatedTvShowsUseCase.refresh() }
    },{
        fetchList(this) { getTopRatedTvShowsUseCase.fetchMore() }
    })

    private val onTheAirListState = ListState({
        fetchList(this) { getOnTheAirTvShowsUseCase.refresh() }
    },{
        fetchList(this) { getOnTheAirTvShowsUseCase.fetchMore() }
    })

    private fun fetchList(listState: ListState, fetchFunction: suspend ()->Unit){
        viewModelScope.launch {
            try {
                fetchFunction()
            } catch (e: Exception) {
                e.printStackTrace()
                //todo handle error
            } finally {
                listState.finishLoading()
            }
        }
    }

    val uiState: StateFlow<TvShowsUiState> by lazy {
        popularListState.refresh()
        topRatedListState.refresh()
        onTheAirListState.refresh()
        combine(
            getPopularTvShowsUseCase.popularTvShows.map { it.map(TvShowPreview::toHighlightedItem) },
            getTopRatedTvShowsUseCase.topRatedTvShows.map { it.map(TvShowPreview::toPosterItem) },
            getOnTheAirTvShowsUseCase.onTheAirTvShows.map { it.map(TvShowPreview::toPosterItem) },
            getWatchlistTvShowsUseCase().map { it.map(TvShowDetail::toHighlightedItem) }
        ) { popularTvShows, topRatedTvShows, onTheAirTvShows, watchlistTvShows ->
            if(popularTvShows.isEmpty() || topRatedTvShows.isEmpty() || onTheAirTvShows.isEmpty()){
                TvShowsUiState.Loading
            } else {
                val tvShowUiState: TvShowsUiState = TvShowsUiState.Content(
                    popularTvShows,
                    topRatedTvShows,
                    onTheAirTvShows,
                    watchlistTvShows
                )
                tvShowUiState
            }
        }.catch { throwable ->
            throwable.printStackTrace()
            emit(TvShowsUiState.Error)
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            TvShowsUiState.Loading
        )
    }

    fun onTopRatedTvShowsThreshold(){
        topRatedListState.thresholdReached()
    }

    fun onOnTheAirTvShowsThreshold(){
        onTheAirListState.thresholdReached()
    }

    fun onPopularTvShowsThreshold(){
        popularListState.thresholdReached()
    }
}