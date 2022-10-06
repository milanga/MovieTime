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
import com.movietime.core.presentation.UIContentState
import com.movietime.main.views.ListSection
import com.movietime.main.views.SectionTitle
import com.movietime.movie.detail.R
import com.movietime.movie.detail.presentation.MovieDetailViewModel
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MovieDetailView(uiState: MovieDetailViewModel.MovieDetailUiState, onMovieSelected: (id: Int) -> Unit, onBackNavigation: () -> Unit, onRecommendationsThresholdReached: () -> Unit){
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
        if (uiState is MovieDetailViewModel.MovieDetailUiState.Content && uiState.movieDetail is UIContentState.ContentState) {
            uiState.movieDetail.content.title
        } else
            ""



    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
        topBar = {
            topBar(showAppBarTitle, title, onBackNavigation, {iconWidth = it}, {appBarHeight = it})
        }
    ) {
        when (uiState) {
            is MovieDetailViewModel.MovieDetailUiState.Error -> DetailErrorScreen(
                uiState
            )
            is MovieDetailViewModel.MovieDetailUiState.Content -> DetailContent(
                uiState,
                listState,
                onMovieSelected,
                CollapsableConfig(appBarHeight, iconWidth - appBarHorizontalPadding, MaterialTheme.typography.titleLarge.fontSize)
            ) {
                onRecommendationsThresholdReached()
            }

        }
    }
}

@ExperimentalMaterial3Api
@Composable
private fun topBar(
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
    content: MovieDetailViewModel.MovieDetailUiState.Content,
    listState: LazyListState,
    onMovieSelected: (id: Int) -> Unit,
    collapsableTitleConfig: CollapsableConfig,
    onRecommendationsThresholdReached: () -> Unit
){
    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {

        item{
            when (content.movieDetail) {
                is UIContentState.Loading<*> -> CollapsibleBackdropTitle(loading = true, listState = listState)
                is UIContentState.ContentState -> CollapsibleBackdropTitle(
                    backdropUrl = content.movieDetail.content.backdropPath,
                    title = content.movieDetail.content.title,
                    collapsableConfig = collapsableTitleConfig,
                    listState = listState
                )
            }
        }

        item{
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            when (content.movieDetail) {
                is UIContentState.Loading<*> -> LoadingBlock(
                    Modifier
                        .fillMaxWidth()
                        .height(140.dp)
                        .padding(horizontal = 16.dp)
                )
                is UIContentState.ContentState -> Overview(content.movieDetail.content.overview)
            }
        }

        item{
            Spacer(modifier = Modifier.height(8.dp))
        }

        item {
            when (content.movieDetail) {
                is UIContentState.Loading<*> -> LoadingBlock(
                    Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .padding(horizontal = 16.dp)
                        .padding(top = 8.dp)
                )
                is UIContentState.ContentState -> {
                    val tagline = content.movieDetail.content.tagline
                    if (!tagline.isNullOrEmpty()){
                        Tagline(tagline)
                    }
                }
            }
        }

        item {
            when (content.movieVideos) {
                is UIContentState.Loading<*> -> VideoView(loading = true)
                is UIContentState.ContentState -> {
                    if (content.movieVideos.content.size > 0) {
                        VideoView(content.movieVideos.content.first().key)
                    }
                }
            }
        }

        item {
            SectionTitle(
                stringResource(R.string.recommendations),
                Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)
            )
        }

        item {
            when (content.movieRecommendations) {
                is UIContentState.Loading<*> -> ListSection(loading = true)
                is UIContentState.ContentState -> {
                    ListSection(
                        content.movieRecommendations.content,
                        onMovieSelected,
                        Modifier
                            .padding(
                                WindowInsets.navigationBars
                                    .only(WindowInsetsSides.Bottom)
                                    .asPaddingValues()
                            )
                            .padding(top = 8.dp, bottom = 16.dp),
                        onRecommendationsThresholdReached,
                    )
                }
            }
        }
    }
}


@Composable
private fun DetailErrorScreen(
    error: MovieDetailViewModel.MovieDetailUiState.Error
){
    Box(modifier = Modifier.fillMaxSize()){
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
            .wrapContentHeight()
            .placeholder(
                loading,
                MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
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
        style = MaterialTheme.typography.headlineSmall,
        color = MaterialTheme.colorScheme.onSurface,
        modifier = Modifier
            .padding(top = 8.dp, start = 16.dp, end = 16.dp)
            .placeholder(
                loading,
                MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
            )
    )
}

@Composable
private fun Overview(
    overview: String
) {
    Text(
        text = overview,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurface,
        modifier = Modifier
            .padding(horizontal = 16.dp)
    )
}

@Composable
private fun LoadingBlock(
    modifier: Modifier = Modifier
){
    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
    ){}
}