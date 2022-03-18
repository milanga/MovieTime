package com.milanga.movietime

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import com.milanga.compose.AppTheme
import com.milanga.movietime.home.Home
import com.google.accompanist.insets.ProvideWindowInsets

@ExperimentalAnimationApi
@Composable
fun MainCompose(){
    ProvideWindowInsets {
        AppTheme {
            Home()
        }
    }
}