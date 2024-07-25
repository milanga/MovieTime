package com.movietime.core.views.video

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.accompanist.placeholder.material.placeholder
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@Composable
fun VideoView(
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