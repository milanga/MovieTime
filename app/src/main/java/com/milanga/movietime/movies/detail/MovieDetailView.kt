package com.milanga.movietime.movies

import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.milanga.movietime.core.UIContentState
import com.milanga.movietime.movies.detail.MovieDetail
import com.milanga.movietime.movies.detail.MovieDetailViewModel
import com.milanga.movietime.movies.detail.Video
import com.milanga.movietime.views.ListSection
import com.milanga.movietime.views.LoadingList
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView


@ExperimentalMaterial3Api
@Composable
fun MovieDetail(viewModel: MovieDetailViewModel = hiltViewModel(), onMovieSelected: (id: Int) -> Unit) {
    val sysUiController = rememberSystemUiController()
    sysUiController.setSystemBarsColor(
        color = MaterialTheme.colorScheme.surfaceVariant
    )
    val lifecycleOwner = LocalLifecycleOwner.current
    val uiStateStateFlowLifecycleAware = remember(viewModel.uiState, lifecycleOwner) {
        viewModel.uiState.flowWithLifecycle(
            lifecycleOwner.lifecycle,
            Lifecycle.State.STARTED
        )
    }
    val uiState = uiStateStateFlowLifecycleAware.collectAsState(MovieDetailViewModel.MovieDetailUiState.Content()).value

    when(uiState){
        is MovieDetailViewModel.MovieDetailUiState.Error -> DetailErrorScreen(uiState)
        is MovieDetailViewModel.MovieDetailUiState.Content -> DetailContent(uiState, onMovieSelected, {viewModel.onRecommendationsThreshold()})
    }
}

@ExperimentalMaterial3Api
@Composable
private fun DetailContent(
    content: MovieDetailViewModel.MovieDetailUiState.Content,
    onMovieSelected: (id: Int) -> Unit,
    onRecommendationsThresholdReached: () -> Unit
){
    val scrollState = rememberScrollState()
    val decayAnimationSpec = rememberSplineBasedDecay<Float>()
    val scrollBehavior = remember(decayAnimationSpec) {
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(decayAnimationSpec)
    }
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            when (content.movieDetail){
                is UIContentState.Loading<*> -> Text("Loading", modifier = Modifier.fillMaxSize())
                is UIContentState.ContentState -> {
                    TopAppBar(content.movieDetail.content, scrollBehavior)
                }
            }
        }
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = rememberInsetsPaddingValues(
                LocalWindowInsets.current.systemBars,
                applyTop = false,
                additionalBottom = 24.dp
            )
        ) {
            item {
                when (content.movieDetail) {
                    is UIContentState.Loading<*> -> Text(
                        "Loading",
                        modifier = Modifier.fillMaxSize()
                    )
                    is UIContentState.ContentState -> {
                        SummarySection(content.movieDetail.content, innerPadding)
                    }
                }
            }

            item {
                when (content.movieVideos) {
                    is UIContentState.Loading<*> -> Text(
                        "Loading",
                        modifier = Modifier.fillMaxSize()
                    )
                    is UIContentState.ContentState -> {
                        if (content.movieVideos.content.size > 0) {
                            VideoView(content.movieVideos.content.first())
                        }
                    }
                }
            }

            item {
                when (content.movieRecommendations) {
                    is UIContentState.Loading<*> -> LoadingList()
                    is UIContentState.ContentState -> {
                        ListSection(
                            content.movieRecommendations,
                            "Recommendations",
                            onMovieSelected,
                            onRecommendationsThresholdReached
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun DetailErrorScreen(error: MovieDetailViewModel.MovieDetailUiState.Error){
    Text("error")
}

@Composable
private fun VideoView(videoInfo: Video){
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    val youtubePlayer = remember {
        YouTubePlayerView(context).apply {
            lifecycleOwner.lifecycle.addObserver(this)
            enableAutomaticInitialization = false

            initialize(object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    youTubePlayer.cueVideo(videoInfo.key, 0f)
                }
            })
        }
    }
    AndroidView(
        {
            youtubePlayer
        }, modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    )
}

@ExperimentalMaterial3Api
@OptIn(ExperimentalCoilApi::class)
@Composable
private fun SummarySection(movieDetail: MovieDetail, innerPadding: PaddingValues){
    Surface(
        modifier = Modifier.padding(16.dp),
        tonalElevation = 3.dp,
        shadowElevation = 3.dp,
        shape = RoundedCornerShape(5.dp)
    ) {
        Image(
            painter = rememberImagePainter(
                data = movieDetail.getBackdropUrl(),
                builder = {
                    crossfade(true)
                }
            ),
            contentScale = ContentScale.Crop,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.5f)
                .clipToBounds()
        )
    }

    Text(
        text = movieDetail.overview,
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier
            .padding(innerPadding)
            .padding(horizontal = 16.dp)
    )

    Text(
        text = movieDetail.tagline.orEmpty(),
        style = MaterialTheme.typography.headlineSmall,
        modifier = Modifier.padding(top = 16.dp, start = 16.dp)
    )
}

@Composable
private fun TopAppBar(
    movieDetail: MovieDetail,
    scrollBehavior: TopAppBarScrollBehavior
) {
    LargeTopAppBar(
        title = {
            Text(
                text = movieDetail.title,
                maxLines = 2
            )
        },
        navigationIcon = {
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Navigate back"
                )
            }
        },
        scrollBehavior = scrollBehavior,
        modifier = Modifier
            .padding(
                rememberInsetsPaddingValues(
                    LocalWindowInsets.current.systemBars,
                    applyBottom = false
                )
            )
    )
}


