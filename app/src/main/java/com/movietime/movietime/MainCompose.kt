package com.movietime.movietime

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import com.movietime.compose.AppTheme
import com.google.accompanist.insets.ProvideWindowInsets
import com.movietime.movietime.main.Main

@ExperimentalAnimationApi
@Composable
fun MainCompose(){
    ProvideWindowInsets {
        AppTheme {
            Main()
        }
    }
}