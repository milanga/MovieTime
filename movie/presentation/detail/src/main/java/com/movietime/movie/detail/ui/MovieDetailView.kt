package com.movietime.movie.detail.ui

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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.movietime.core.views.WatchlistFab
import com.movietime.core.views.collapsibleBackddropTitle.CollapsableConfig
import com.movietime.core.views.collapsibleBackddropTitle.CollapsibleBackdropTitle
import com.movietime.core.views.overview.Overview
import com.movietime.core.views.poster.ListSection
import com.movietime.core.views.poster.model.PosterItem
import com.movietime.core.views.tagline.Tagline
import com.movietime.core.views.topbar.TopBar
import com.movietime.core.views.video.VideoView
import com.movietime.main.views.SectionTitle
import com.movietime.movie.detail.R
import com.movietime.movie.detail.presentation.MovieDetailViewModel
import com.movietime.movie.detail.presentation.model.MovieDetailUiState
import com.movietime.movie.detail.presentation.model.UiMovieDetail
import com.movietime.movie.detail.presentation.model.UiVideo


@OptIn(ExperimentalLifecycleComposeApi::class)
@ExperimentalMaterial3Api
@Composable
fun MovieDetailRoute(
    viewModel: MovieDetailViewModel = hiltViewModel(),
    onMovieSelected: (id: Int) -> Unit,
    onBackNavigation: () -> Unit
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    MovieDetailView(
        uiState = uiState,
        onMovieSelected = onMovieSelected,
        onBackNavigation = {onBackNavigation()},
        onRecommendationsThresholdReached = {viewModel.onRecommendationsThreshold()},
        onToggleWatchlist = { viewModel.toggleWatchlist() }
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
private fun MovieDetailView(
    uiState: MovieDetailUiState,
    onMovieSelected: (id: Int) -> Unit,
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
        if (uiState is MovieDetailUiState.Content) {
            uiState.movieDetail.title
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
            if (uiState is MovieDetailUiState.Content) {
                WatchlistFab(
                    onClick = onToggleWatchlist,
                    add = !uiState.isMovieInWatchlist
                )
            }
        }
    ) { padding ->
        when (uiState) {
            is MovieDetailUiState.Error -> DetailErrorScreen(
                Modifier.consumedWindowInsets(padding)
            )
            is MovieDetailUiState.Content -> DetailContent(
                movieDetail = uiState.movieDetail,
                movieVideos = uiState.movieVideos,
                movieRecommendations = uiState.movieRecommendations,
                listState = listState,
                onMovieSelected = onMovieSelected,
                collapsableTitleConfig = CollapsableConfig(appBarHeight, iconWidth - appBarHorizontalPadding, MaterialTheme.typography.titleLarge.fontSize),
                modifier = Modifier.consumedWindowInsets(padding)
            ) {
                onRecommendationsThresholdReached()
            }
            is MovieDetailUiState.Loading -> DetailContent(
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
    movieDetail: UiMovieDetail = UiMovieDetail("","","",""),
    movieVideos: List<UiVideo> = emptyList(),
    movieRecommendations: List<PosterItem> = emptyList(),
    listState: LazyListState,
    onMovieSelected: (id: Int) -> Unit = {},
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
                backdropUrl = movieDetail.backdropPath,
                title = movieDetail.title,
                collapsableConfig = collapsableTitleConfig,
                listState = listState,
                loading = loading
            )
        }

        item{
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            Overview(movieDetail.overview, loading)
        }

        item{
            Spacer(modifier = Modifier.height(8.dp))
        }

        item {
            val tagline = movieDetail.tagline
            if (loading || tagline.isNotEmpty()) {
                Tagline(tagline, loading)
            }
        }

        if(loading || movieVideos.isNotEmpty()) {
            item {
                VideoView(movieVideos.firstOrNull()?.key.orEmpty(), loading = loading)
            }
        }

        if(loading || movieRecommendations.isNotEmpty()){
            item {
                SectionTitle(
                    stringResource(R.string.recommendations),
                    Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp),
                    loading
                )
            }

            item {
                ListSection(
                    posterList = movieRecommendations,
                    onItemSelected = onMovieSelected,
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



