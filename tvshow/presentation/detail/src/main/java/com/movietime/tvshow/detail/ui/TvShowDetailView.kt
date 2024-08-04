package com.movietime.tvshow.detail.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.consumedWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.movietime.core.views.WatchlistFab
import com.movietime.core.views.collapsibleBackddropTitle.CollapsableConfig
import com.movietime.core.views.collapsibleBackddropTitle.CollapsibleBackdropTitle
import com.movietime.core.views.detail.DetailRowData
import com.movietime.core.views.detail.DetailSection
import com.movietime.core.views.overview.Overview
import com.movietime.core.views.poster.ListSection
import com.movietime.core.views.poster.model.MediaType
import com.movietime.core.views.poster.model.PosterItem
import com.movietime.core.views.tag.TagSection
import com.movietime.core.views.tagline.Tagline
import com.movietime.core.views.topbar.TopBar
import com.movietime.core.views.video.VideoView
import com.movietime.main.views.SectionTitle
import com.movietime.tvshow.detail.R
import com.movietime.tvshow.detail.presentation.TvShowDetailViewModel
import com.movietime.tvshow.detail.presentation.model.TvShowDetailUiState
import com.movietime.tvshow.detail.presentation.model.UiTvShowDetail
import com.movietime.tvshow.detail.presentation.model.UiVideo


@OptIn(ExperimentalLifecycleComposeApi::class)
@ExperimentalMaterial3Api
@Composable
fun TvShowDetailRoute(
    viewModel: TvShowDetailViewModel = hiltViewModel(),
    onTvShowSelected: (id: Int) -> Unit,
    onBackNavigation: () -> Unit
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    TvShowDetailView(
        uiState = uiState,
        onTvShowSelected = onTvShowSelected,
        onBackNavigation = {onBackNavigation()},
        onRecommendationsThresholdReached = {viewModel.onRecommendationsThreshold()},
        onToggleWatchlist = {viewModel.toggleTvShowFromWatchList()},
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
private fun TvShowDetailView(
    uiState: TvShowDetailUiState,
    onTvShowSelected: (id: Int) -> Unit,
    onBackNavigation: () -> Unit,
    onRecommendationsThresholdReached: () -> Unit,
    onToggleWatchlist: () -> Unit
){
    val listState = rememberLazyListState()
    var appBarHeight by remember { mutableStateOf(0) }
    var iconWidth by remember { mutableStateOf(0) }
    val appBarHorizontalPadding = with(LocalDensity.current){(8.dp).toPx()}
    val showAppBarTitle by remember {
        derivedStateOf {
            val visibleItemsInfo = listState.layoutInfo.visibleItemsInfo
            val threshold = appBarHeight
            visibleItemsInfo.isNotEmpty() && (visibleItemsInfo[0].index > 0 || visibleItemsInfo[0].size + visibleItemsInfo[0].offset <= threshold)
        }
    }

    val title =
        if (uiState is TvShowDetailUiState.Content) {
            uiState.tvShowDetail.title
        } else
            ""
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
        topBar = {
            TopBar(showAppBarTitle, title, onBackNavigation, {iconWidth = it}, {appBarHeight = it})
        },
        floatingActionButton = {
            if (uiState is TvShowDetailUiState.Content) {
                WatchlistFab(
                    onClick = onToggleWatchlist,
                    add = !uiState.isTvShowInWatchlist
                )
            }
        }
    ) { padding ->
        when (uiState) {
            is TvShowDetailUiState.Error -> DetailErrorScreen(
                Modifier.consumedWindowInsets(padding)
            )
            is TvShowDetailUiState.Content -> DetailContent(
                tvShowDetail = uiState.tvShowDetail,
                tvShowVideos = uiState.tvShowVideos,
                tvShowRecommendations = uiState.tvShowRecommendations,
                listState = listState,
                onTvShowSelected = onTvShowSelected,
                collapsableTitleConfig = CollapsableConfig(appBarHeight, iconWidth - appBarHorizontalPadding, MaterialTheme.typography.titleLarge.fontSize),
                modifier = Modifier.consumedWindowInsets(padding),
            ) {
                onRecommendationsThresholdReached()
            }
            is TvShowDetailUiState.Loading -> DetailContent(
                modifier=Modifier.consumedWindowInsets(padding),
                collapsableTitleConfig = CollapsableConfig(appBarHeight, iconWidth - appBarHorizontalPadding, MaterialTheme.typography.titleLarge.fontSize),
                listState = listState,
                loading = true
            )
        }
    }
}

@ExperimentalMaterial3Api
@Composable
private fun DetailContent(
    modifier: Modifier = Modifier,
    tvShowDetail: UiTvShowDetail = UiTvShowDetail(),
    tvShowVideos: List<UiVideo> = emptyList(),
    tvShowRecommendations: List<PosterItem> = emptyList(),
    listState: LazyListState,
    onTvShowSelected: (id: Int) -> Unit = {},
    collapsableTitleConfig: CollapsableConfig,
    loading: Boolean = false,
    onRecommendationsThresholdReached: () -> Unit = {}
){
    LazyColumn(
        state = listState,
        modifier = modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {

        item{
            CollapsibleBackdropTitle(
                backdropUrl = tvShowDetail.backdropUrl,
                title = tvShowDetail.title,
                collapsableConfig = collapsableTitleConfig,
                listState = listState,
                loading = loading
            )
        }

        item{
            Spacer(modifier = Modifier.height(16.dp))
        }

        item{
            DetailSection(
                tvShowDetail.posterUrl,
                detailRowData(tvShowDetail),
                Modifier.padding(horizontal = 16.dp)
            )
        }

        item{
            Spacer(modifier = Modifier.height(8.dp))
        }

        item {
            TagSection(
                tags = tvShowDetail.genres,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
        }

        item {
            SectionTitle(
                stringResource(R.string.overview),
                Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp),
                loading
            )
        }

        item {
            val tagline = tvShowDetail.tagline
            if (loading || tagline.isNotEmpty()) {
                Tagline(
                    Modifier
                        .padding(top = 2.dp, start = 16.dp, end = 16.dp),
                    tagline,
                    loading
                )
            }
        }

        item{
            Spacer(modifier = Modifier.height(4.dp))
        }

        item {
            Overview(tvShowDetail.overview, loading)
        }

        item{
            Spacer(modifier = Modifier.height(8.dp))
        }

        if(loading || tvShowVideos.isNotEmpty()) {
            item {
                VideoView(tvShowVideos.firstOrNull()?.key.orEmpty(), loading = loading)
            }
        }

        if(loading || tvShowRecommendations.isNotEmpty()){
            item {
                SectionTitle(
                    stringResource(R.string.recommendations),
                    Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp),
                    loading
                )
            }

            item {
                ListSection(
                    posterList = tvShowRecommendations,
                    onItemSelected = onTvShowSelected,
                    modifier = Modifier
                        .padding(
                            WindowInsets.navigationBars
                                .only(WindowInsetsSides.Bottom)
                                .asPaddingValues()
                        )
                        .padding(top = 8.dp, bottom = 16.dp),
                    onScrollThresholdReached = onRecommendationsThresholdReached,
                    loading = loading
                )
            }
        }
    }
}


@Composable
private fun DetailErrorScreen(
    modifier: Modifier
){
    Box(modifier = modifier.fillMaxSize()){
        Text("error", Modifier.align(Alignment.Center))
    }
}

@Composable
private fun detailRowData(movieDetail: UiTvShowDetail): MutableList<DetailRowData> {
    val detailRows = mutableListOf<DetailRowData>()
    createDetailRow(
        movieDetail.rating,
        R.drawable.grade,
        stringResource(R.string.rate)
    )?.let { detailRows.add(it) }

    createDetailRow(
        movieDetail.duration,
        R.drawable.clock,
        stringResource(R.string.duration)
    )?.let { detailRows.add(it) }

    createDetailRow(
        movieDetail.releaseDate,
        R.drawable.event_coming,
        stringResource(R.string.release_date)
    )?.let { detailRows.add(it) }
    return detailRows
}

@Preview
@Composable
private fun PreviewTvShowDetail(){
    TvShowDetailView(
        uiState = TvShowDetailUiState.Content(
            UiTvShowDetail(
                title = "Breaking Bad",
                backdropUrl = "http://image.tmdb.org/t/p/original/avedvodAZUcwqevBfm8p4G2NziQ.jpg",
                overview = "Imprisoned in the 1940s for the double murder of his wife and her lover, upstanding banker Andy Dufresne begins a new life at the Shawshank prison, where he puts his accounting skills to work for an amoral warden. During his long stretch in prison, Dufresne comes to be admired by the other inmates -- including an older prisoner named Red -- for his integrity and unquenchable sense of hope.",
                tagline = "Fear can hold you prisoner. Hope can set you free.",
                posterUrl = "url",
                rating = "8.6",
                releaseDate = "28-02-2024",
                duration = "115",
                genres = listOf("Action", "Comedy")
            ),
            tvShowVideos = listOf(UiVideo("pYmAy3H0s3Q")),
            tvShowRecommendations = listOf(PosterItem(1, "http://image.tmdb.org/t/p/w500/3bhkrj58Vtu7enYsRolD1fZdja1.jpg", "9.8", MediaType.Movie)),
            isTvShowInWatchlist = false
        ),
        onTvShowSelected = {},
        onBackNavigation = {},
        onRecommendationsThresholdReached = {},
        onToggleWatchlist = {}
    )
}

fun createDetailRow(
    text: String?,
    iconId: Int,
    contentDescription: String
): DetailRowData? {
    return if(text.isNullOrBlank()){
        null
    } else {
        DetailRowData(text, iconId, contentDescription)
    }
}


