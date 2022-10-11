package com.movietime.movie.detail.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.placeholder.material.placeholder
import com.movietime.core.views.ListSection
import com.movietime.core.views.model.PosterItem
import com.movietime.main.views.SectionTitle
import com.movietime.movie.detail.R
import com.movietime.movie.detail.presentation.MovieDetailViewModel
import com.movietime.movie.detail.presentation.model.MovieDetailUiState
import com.movietime.movie.detail.presentation.model.UiMovieDetail
import com.movietime.movie.detail.presentation.model.UiVideo
import com.movietime.movie.detail.ui.collapsibleBackddropTitle.CollapsableConfig
import com.movietime.movie.detail.ui.collapsibleBackddropTitle.CollapsibleBackdropTitle
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView


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
        onBackNavigation = {onBackNavigation()}
    ){viewModel.onRecommendationsThreshold()}
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
private fun MovieDetailView(uiState: MovieDetailUiState, onMovieSelected: (id: Int) -> Unit, onBackNavigation: () -> Unit, onRecommendationsThresholdReached: () -> Unit){
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
private fun TopBar(
    showTopAppBar: Boolean,
    title: String,
    onBackNavigation: () -> Unit,
    onIconWidthChanged: (Int) -> Unit,
    onAppBarHeightChanged: (Int) -> Unit
) {
    val backgroundAlpha by animateFloatAsState(targetValue = if (showTopAppBar) 1f else 0f)
    val statusHeight = with(LocalDensity.current) { WindowInsets.statusBars.getTop(this).toDp() }
    val topBarHeight = 64.dp
    val appBarBackgroundColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp)
    Box {
        Surface(
            color = appBarBackgroundColor,
            modifier = Modifier
                .fillMaxWidth()
                .height(topBarHeight + statusHeight)
                .graphicsLayer { alpha = backgroundAlpha }
        ) {}

        TopAppBar(
            title = {
                if (showTopAppBar) {
                    Text(title)
                }
            },
            colors = TopAppBarDefaults.smallTopAppBarColors(Color.Transparent),
            navigationIcon = {
                IconButton(
                    onClick = { onBackNavigation() },
                    modifier = Modifier
                        .background(
                            color = appBarBackgroundColor.copy(alpha = 0.4f),
                            shape = CircleShape
                        )
                        .onSizeChanged { size ->
                            onIconWidthChanged(size.width)
                        }
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .onSizeChanged { size ->
                    onAppBarHeightChanged(size.height)
                }
        )
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
                    onMovieSelected = onMovieSelected,
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
private fun VideoView(
    videoKey: String = "",
    loading: Boolean = false
){
    val lifecycleOwner = LocalLifecycleOwner.current
    AndroidView(
        { context ->
            YouTubePlayerView(context).apply {
                lifecycleOwner.lifecycle.addObserver(this)
                enableAutomaticInitialization = false
                initialize(object : AbstractYouTubePlayerListener() {
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        youTubePlayer.cueVideo(videoKey, 0f)
                    }
                })
            }
        }, modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, top = 8.dp)
            .fillMaxWidth()
            .placeholder(
                loading,
                MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp)
            )
    )
}

@Composable
private fun Tagline(
    tagline: String = "",
    loading: Boolean = false
) {
    Text(
        text = tagline,
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.onSurface,
        modifier = Modifier
            .padding(top = 8.dp, start = 16.dp, end = 16.dp)
            .fillMaxWidth()
            .placeholder(
                loading,
                MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp)
            )
    )
}

@Composable
private fun Overview(
    overview: String,
    loading: Boolean = false
) {
    val loadingModifier = if(loading){
        Modifier.height(140.dp)
    } else {
        Modifier
    }
    Text(
        text = overview,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurface,
        modifier = loadingModifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .placeholder(
                loading,
                MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp)
            )
    )
}