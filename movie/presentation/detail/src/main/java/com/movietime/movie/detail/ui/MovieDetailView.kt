@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.movietime.movie.detail.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.movietime.core.views.SharedElementType
import com.movietime.core.views.SharedKeys
import com.movietime.core.views.WatchlistFab
import com.movietime.core.views.collapsibleBackddropTitle.CollapsableConfig
import com.movietime.core.views.collapsibleBackddropTitle.CollapsibleBackdropTitle
import com.movietime.core.views.detail.DetailRowData
import com.movietime.core.views.detail.DetailSection
import com.movietime.core.views.overview.Overview
import com.movietime.core.views.poster.ListSection
import com.movietime.core.views.poster.PosterImage
import com.movietime.core.views.poster.PosterItemView
import com.movietime.core.views.poster.model.MediaType
import com.movietime.core.views.poster.model.PosterItem
import com.movietime.core.views.tag.TagSection
import com.movietime.core.views.tagline.Tagline
import com.movietime.core.views.topbar.TopBar
import com.movietime.core.views.video.VideoView
import com.movietime.main.views.SectionTitle
import com.movietime.movie.detail.R
import com.movietime.movie.detail.presentation.MovieDetailViewModel
import com.movietime.movie.detail.presentation.model.MovieDetailUiState
import com.movietime.movie.detail.presentation.model.UiMovieDetail
import com.movietime.movie.detail.presentation.model.UiVideo


@ExperimentalMaterial3Api
@Composable
fun MovieDetailRoute(
    transitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    viewModel: MovieDetailViewModel = hiltViewModel(),
    onMovieSelected: (id: Int) -> Unit,
    onBackNavigation: () -> Unit
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    MovieDetailView(
        transitionScope = transitionScope,
        animatedContentScope = animatedContentScope,
        uiState = uiState,
        onMovieSelected = onMovieSelected,
        onBackNavigation = {onBackNavigation()},
        onRecommendationsThresholdReached = {viewModel.onRecommendationsThreshold()},
        onToggleWatchlist = { viewModel.toggleWatchlist() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MovieDetailView(
    transitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
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
                Modifier.consumeWindowInsets(padding)
            )
            is MovieDetailUiState.Content -> DetailContent(
                transitionScope = transitionScope,
                animatedContentScope = animatedContentScope,
                origin = uiState.origin,
                movieDetail = uiState.movieDetail,
                movieVideos = uiState.movieVideos,
                movieRecommendations = uiState.movieRecommendations,
                listState = listState,
                onMovieSelected = onMovieSelected,
                collapsableTitleConfig = CollapsableConfig(appBarHeight, iconWidth - appBarHorizontalPadding, MaterialTheme.typography.titleLarge.fontSize),
                modifier = Modifier.consumeWindowInsets(padding)
            ) {
                onRecommendationsThresholdReached()
            }
            is MovieDetailUiState.Loading -> DetailContent(
                transitionScope = transitionScope,
                animatedContentScope = animatedContentScope,
                origin = "",
                modifier = Modifier.consumeWindowInsets(padding),
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
    transitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    origin: String,
    modifier: Modifier = Modifier,
    movieDetail: UiMovieDetail = UiMovieDetail(),
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
                backdropUrl = movieDetail.backdropUrl,
                title = movieDetail.title,
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
                movieDetail.posterUrl,
                detailRowData(movieDetail),
                Modifier.padding(horizontal = 16.dp),
                loading = loading
            ) {
                PosterItemView(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                        .aspectRatio(0.67f),
                    loading = loading
                ) { posterShape ->
                    with(transitionScope) {
                        PosterImage(
                            posterUrl = movieDetail.posterUrl,
                            modifier = Modifier
                                .sharedElement(
                                    rememberSharedContentState(
                                        key = SharedKeys(
                                            id = movieDetail.id,
                                            origin = origin,
                                            type = SharedElementType.Image
                                        )
                                    ),
                                    animatedContentScope
                                )
                                .fillMaxSize()
                                .clip(posterShape)
                        )
                    }
                }
            }
        }

        item{
            Spacer(modifier = Modifier.height(8.dp))
        }

        item {
            TagSection(
                tags = movieDetail.genres,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
        }

        item {
            SectionTitle(
                "Overview",
                Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp),
                loading
            )
        }


        item {
            val tagline = movieDetail.tagline
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
            Overview(movieDetail.overview, loading)
        }

        item{
            Spacer(modifier = Modifier.height(8.dp))
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
                    modifier = Modifier
                        .padding(
                            WindowInsets.navigationBars
                                .only(WindowInsetsSides.Bottom)
                                .asPaddingValues()
                        )
                        .padding(top = 8.dp, bottom = 16.dp),
                    onScrollThresholdReached = onRecommendationsThresholdReached,
                    loading = loading
                ) { posterItem ->
                    PosterItemView(
                        Modifier
                            .padding(horizontal = 8.dp)
                            .width(130.dp)
                            .aspectRatio(0.67f),
                        posterItem.rating,
                        { onMovieSelected(posterItem.id) }
                    ) { posterShape ->
                        PosterImage(
                            posterUrl = posterItem.posterUrl,
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(posterShape)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun detailRowData(movieDetail: UiMovieDetail): MutableList<DetailRowData> {
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


@Composable
private fun DetailErrorScreen(
    modifier: Modifier
){
    Box(modifier = modifier.fillMaxSize()){
        Text("error", Modifier.align(Alignment.Center))
    }
}

@Preview
@Composable
private fun PreviewMovieDetail(){
    val movieDetail = UiMovieDetail(
        title = "The Shawshank Redemption",
        backdropUrl = "http://image.tmdb.org/t/p/original/avedvodAZUcwqevBfm8p4G2NziQ.jpg",
        overview = "Imprisoned in the 1940s for the double murder of his wife and her lover, upstanding banker Andy Dufresne begins a new life at the Shawshank prison, where he puts his accounting skills to work for an amoral warden. During his long stretch in prison, Dufresne comes to be admired by the other inmates -- including an older prisoner named Red -- for his integrity and unquenchable sense of hope.",
        tagline = "Fear can hold you prisoner. Hope can set you free.",
        posterUrl = "url",
        rating = "8.6",
        releaseDate = "28-02-2024",
        duration = "115",
        genres = listOf("Action", "Comedy")
    )
    SharedTransitionLayout {
        AnimatedContent(movieDetail) { _ ->
            MovieDetailView(
                transitionScope = this@SharedTransitionLayout,
                animatedContentScope = this@AnimatedContent,
                uiState = MovieDetailUiState.Content(
                    movieDetail,
                    movieVideos = listOf(UiVideo("pYmAy3H0s3Q")),
                    movieRecommendations = listOf(
                        PosterItem(
                            1,
                            "http://image.tmdb.org/t/p/w500/3bhkrj58Vtu7enYsRolD1fZdja1.jpg",
                            "9.8",
                            MediaType.Movie
                        )
                    ),
                    isMovieInWatchlist = false,
                    origin = "Recommended"
                ),
                onMovieSelected = {},
                onBackNavigation = {},
                onRecommendationsThresholdReached = {},
                onToggleWatchlist = {}
            )
        }
    }
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



