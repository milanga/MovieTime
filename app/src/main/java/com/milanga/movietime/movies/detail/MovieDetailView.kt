package com.milanga.movietime.movies

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.milanga.compose.black25Opacity
import com.milanga.movietime.R
import com.milanga.movietime.core.UIContentState
import com.milanga.movietime.movies.detail.MovieDetail
import com.milanga.movietime.movies.detail.MovieDetailViewModel
import com.milanga.movietime.movies.detail.Video
import com.milanga.movietime.views.ListSection
import com.milanga.movietime.views.LoadingList
import com.milanga.movietime.views.SectionTitle
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView


@ExperimentalMaterial3Api
@Composable
fun MovieDetail(viewModel: MovieDetailViewModel = hiltViewModel(), onMovieSelected: (id: Int) -> Unit) {
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
    Box (
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            item {
                when (content.movieDetail) {
                    is UIContentState.Loading<*> -> Text(
                        "Loading",
                        modifier = Modifier.fillMaxSize()
                    )
                    is UIContentState.ContentState -> {
                        SummarySection(content.movieDetail.content)
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
                SectionTitle(
                    stringResource(R.string.recommendations),
                    Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)
                )
            }

            item {
                when (content.movieRecommendations) {
                    is UIContentState.Loading<*> -> LoadingList()
                    is UIContentState.ContentState -> {
                        ListSection(
                            content.movieRecommendations,
                            onMovieSelected,
                            Modifier.padding(
                                rememberInsetsPaddingValues(
                                    LocalWindowInsets.current.systemBars,
                                    applyTop = false,
                                    additionalBottom = 16.dp
                                )
                            ),
                            onRecommendationsThresholdReached,
                        )
                    }
                }
            }
        }
        IconButton(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .padding(
                    rememberInsetsPaddingValues(
                        LocalWindowInsets.current.systemBars,
                        applyBottom = false,
                        additionalStart = 16.dp
                    )
                )
                .background(color = black25Opacity, shape = CircleShape)
                .clip(CircleShape)
        ){
            Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null, tint = Color.White)
        }
    }
}
//}

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
            .padding(start = 16.dp, end = 16.dp, top = 8.dp)
            .fillMaxWidth()
            .wrapContentHeight()
    )
}

@ExperimentalMaterial3Api
@OptIn(ExperimentalCoilApi::class)
@Composable
private fun SummarySection(movieDetail: MovieDetail){
    Box(
        modifier = Modifier
            .padding(bottom = 16.dp)
            .fillMaxWidth()
            .aspectRatio(1.5f)
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
                .fillMaxSize()
                .clipToBounds()
        )

        Text(
            text = movieDetail.title,
            style = MaterialTheme.typography.displaySmall,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            MaterialTheme.colorScheme.surface
                        )
                    )
                )
                .padding(top = 14.dp, start = 16.dp, bottom = 7.dp)
        )
    }

    Text(
        text = movieDetail.overview,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurface,
        modifier = Modifier
            .padding(start = 16.dp, end=16.dp, bottom = 8.dp)
    )

    if (!movieDetail.tagline.isNullOrEmpty()){
        Text(
            text = movieDetail.tagline,
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp)
        )
    }
}


