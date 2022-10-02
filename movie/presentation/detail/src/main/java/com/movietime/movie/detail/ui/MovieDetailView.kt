package com.movietime.movie.detail.ui

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
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
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.movietime.core.presentation.UIContentState
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
    onBackNavigation: () -> Unit,
    contentPadding: PaddingValues
) {
    rememberSystemUiController().setStatusBarColor(color = Color.Transparent, darkIcons = true)
    val lifecycleOwner = LocalLifecycleOwner.current
    val uiStateStateFlowLifecycleAware = remember(viewModel.uiState, lifecycleOwner) {
        viewModel.uiState.flowWithLifecycle(
            lifecycleOwner.lifecycle,
            Lifecycle.State.STARTED
        )
    }
    val uiState = uiStateStateFlowLifecycleAware.collectAsState(MovieDetailViewModel.MovieDetailUiState.Content()).value

    val listState = rememberLazyListState()

    var appBarHeight by remember { mutableStateOf(0) }
    val showTopAppBar by remember {
        derivedStateOf {
            val visibleItemsInfo = listState.layoutInfo.visibleItemsInfo
            val threshold = appBarHeight
            visibleItemsInfo.isNotEmpty() && (visibleItemsInfo[0].index > 0 || visibleItemsInfo[0].size + visibleItemsInfo[0].offset <= threshold)
        }
    }

    val title = if (uiState is MovieDetailViewModel.MovieDetailUiState.Content && uiState.movieDetail is UIContentState.ContentState){
        uiState.movieDetail.content.title
    } else
        ""


    Scaffold(
        modifier = Modifier
            .padding(contentPadding)
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
        topBar = {
            TopAppBar(
                title = {
                    if(showTopAppBar) {
                        Text(title)
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(Color.Transparent),
                navigationIcon = {
                    IconButton(
                        onClick = { onBackNavigation() },
                        modifier = Modifier
                            .background(
                                color = MaterialTheme.colorScheme.surface.copy(alpha = 0.4f),
                                shape = CircleShape
                            )
                    ){
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null, tint = MaterialTheme.colorScheme.onSurface)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .onSizeChanged { size ->
                        Log.d("bar height", size.height.toString())
                        appBarHeight = size.height
                    }
                    .background(if (showTopAppBar) Color.White else Color.Transparent)
            )

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
                !showTopAppBar,
                CollapsableConfig(appBarHeight, with(LocalDensity.current){(40.dp).toPx()}, MaterialTheme.typography.titleLarge.fontSize)
            ) {
                viewModel.onRecommendationsThreshold()
            }

        }
    }
}

@ExperimentalMaterial3Api
@Composable
private fun DetailContent(
    content: MovieDetailViewModel.MovieDetailUiState.Content,
    listState: LazyListState,
    onMovieSelected: (id: Int) -> Unit,
    showTitle: Boolean,
    collapsableConfig: CollapsableConfig,
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
                    collapsableConfig = collapsableConfig,
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

data class CollapsableConfig(val finalTransitionOffset: Int, val finalTranslation: Float, val finalFontSize: TextUnit)

@Composable
@OptIn(coil.annotation.ExperimentalCoilApi::class)
private fun CollapsibleBackdropTitle(
    backdropUrl: String = "",
    title: String = "",
    titleStyle: TextStyle = MaterialTheme.typography.displaySmall,
    loading: Boolean = false,
    collapsableConfig: CollapsableConfig = CollapsableConfig(0,0f, MaterialTheme.typography.displaySmall.fontSize),
    listState: LazyListState
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1.6f)
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                ImageRequest.Builder(LocalContext.current).data(data = backdropUrl).apply(block = fun ImageRequest.Builder.() {
                    crossfade(true)
                }).build()
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
                .offset {
                    val parallaxOffset = listState.firstVisibleItemScrollOffset / 2
                    IntOffset(
                        x = 0,
                        y = parallaxOffset
                    )
                }
                .graphicsLayer {
                    alpha = if (listState.layoutInfo.visibleItemsInfo.isNotEmpty() &&
                        listState.layoutInfo.visibleItemsInfo[0].index == 0
                    ) {
                        1f - listState.firstVisibleItemScrollOffset.toFloat() / (listState.layoutInfo.visibleItemsInfo[0].size - collapsableConfig.finalTransitionOffset)
                    } else {
                        0f
                    }
                }
        )

        if(!loading) {
            var textWidth by remember { mutableStateOf(0) }
            Text(
                text = title,
                style = titleStyle,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
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
                    .graphicsLayer {
                        val finalScaleDifference =
                            1 - (collapsableConfig.finalFontSize.value / titleStyle.fontSize.value)
                        val scale = if (listState.layoutInfo.visibleItemsInfo.isNotEmpty() &&
                            listState.layoutInfo.visibleItemsInfo[0].index == 0
                        ) {
                            1f - finalScaleDifference * listState.firstVisibleItemScrollOffset / (listState.layoutInfo.visibleItemsInfo[0].size - collapsableConfig.finalTransitionOffset)
                        } else {
                            1f
                        }

                        val translation = if (listState.layoutInfo.visibleItemsInfo.isNotEmpty() &&
                            listState.layoutInfo.visibleItemsInfo[0].index == 0
                        ) {
                            (collapsableConfig.finalTranslation - textWidth * finalScaleDifference / 2) * listState.firstVisibleItemScrollOffset / (listState.layoutInfo.visibleItemsInfo[0].size - collapsableConfig.finalTransitionOffset)
                        } else {
                            0f
                        }

                        scaleX = scale
                        scaleY = scale
                        translationX = translation
                    }
                    .onSizeChanged {
                        textWidth = it.width
                    }
            )
        }
    }
}


