package com.movietime.movie.detail.ui

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
import coil.compose.rememberImagePainter
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.google.accompanist.placeholder.placeholder
import com.movietime.core.domain.UIContentState
import com.movietime.core.views.model.PosterItem
import com.movietime.movie.detail.R
import com.movietime.movie.detail.presentation.MovieDetailViewModel
import com.movietime.main.views.ListSection
import com.movietime.main.views.SectionTitle
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView


@ExperimentalMaterial3Api
@Composable
fun MovieDetailView(
    viewModel: MovieDetailViewModel = hiltViewModel(),
    onMovieSelected: (id: Int) -> Unit,
    onBackNavigation: () -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val uiStateStateFlowLifecycleAware = remember(viewModel.uiState, lifecycleOwner) {
        viewModel.uiState.flowWithLifecycle(
            lifecycleOwner.lifecycle,
            Lifecycle.State.STARTED
        )
    }
    val uiState = uiStateStateFlowLifecycleAware.collectAsState(MovieDetailViewModel.MovieDetailUiState.Content()).value

    Box (
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        when (uiState) {
            is MovieDetailViewModel.MovieDetailUiState.Error -> DetailErrorScreen(
                uiState,
                Modifier.align(Alignment.Center)
            )
            is MovieDetailViewModel.MovieDetailUiState.Content -> DetailContent(
                uiState,
                onMovieSelected) {
                    viewModel.onRecommendationsThreshold()
                }

        }
        IconButton(
            onClick = { onBackNavigation() },
            modifier = Modifier
                .padding(
                    rememberInsetsPaddingValues(
                        LocalWindowInsets.current.systemBars,
                        applyBottom = false,
                        additionalStart = 16.dp
                    )
                )
                .background(
                    color = MaterialTheme.colorScheme.surface.copy(alpha = 0.4f),
                    shape = CircleShape
                )
        ){
            Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null, tint = MaterialTheme.colorScheme.onSurface)
        }
    }
}

@ExperimentalMaterial3Api
@Composable
private fun DetailContent(
    content: MovieDetailViewModel.MovieDetailUiState.Content,
    onMovieSelected: (id: Int) -> Unit,
    onRecommendationsThresholdReached: () -> Unit
){
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {

        item{
            when (content.movieDetail) {
                is UIContentState.Loading<*> -> BackdropTitle(loading = true)
                is UIContentState.ContentState -> BackdropTitle(
                    backdropUrl = content.movieDetail.content.backdropPath,
                    title = content.movieDetail.content.title
                )
            }
        }

        item {
            when (content.movieDetail) {
                is UIContentState.Loading<*> -> LoadingBlock(
                    Modifier
                        .fillMaxWidth()
                        .height(140.dp)
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 8.dp)
                )
                is UIContentState.ContentState -> Overview(content.movieDetail.content.overview)
            }
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
                        content.movieRecommendations.content.map{
                            PosterItem(
                                it.id,
                                it.posterPath,
                                it.rating.toString()
                            )
                        },
                        onMovieSelected,
                        Modifier
                            .padding(
                                rememberInsetsPaddingValues(
                                    LocalWindowInsets.current.systemBars,
                                    applyTop = false,
                                    additionalBottom = 16.dp
                                )
                            )
                            .padding(top=8.dp),
                        onRecommendationsThresholdReached,
                    )
                }
            }
        }
    }
}


@Composable
private fun DetailErrorScreen(
    error: MovieDetailViewModel.MovieDetailUiState.Error,
    modifier: Modifier
){
    Text("error", modifier)
}

@Composable
private fun VideoView(
    videoKey: String = "",
    loading: Boolean = false
){
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    val youtubePlayer = remember {
        YouTubePlayerView(context).apply {
            lifecycleOwner.lifecycle.addObserver(this)
            enableAutomaticInitialization = false

            initialize(object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    youTubePlayer.cueVideo(videoKey, 0f)
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
            .padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
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

@Composable
@OptIn(coil.annotation.ExperimentalCoilApi::class)
private fun BackdropTitle(
    backdropUrl: String = "",
    title: String = "",
    loading: Boolean = false
) {
    Box(
        modifier = Modifier
            .padding(bottom = 16.dp)
            .fillMaxWidth()
            .aspectRatio(1.6f)
    ) {
        Image(
            painter = rememberImagePainter(
                data = backdropUrl,
                builder = {
                    crossfade(true)
                }
            ),
            contentScale = ContentScale.Crop,
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .clipToBounds()
                .placeholder(
                    loading,
                    MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                )
        )

        if(!loading) {
            Text(
                text = title,
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
                    .padding(top = 14.dp, start = 16.dp, bottom = 7.dp, end = 16.dp)
            )
        }
    }
}


