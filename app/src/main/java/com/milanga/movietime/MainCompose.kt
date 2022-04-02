package com.milanga.movietime

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import com.milanga.compose.AppTheme
import com.google.accompanist.insets.ProvideWindowInsets
import com.milanga.movietime.main.Main

@ExperimentalAnimationApi
@Composable
fun MainCompose(){
    ProvideWindowInsets {
        AppTheme {
            Main()
        }
    }
}