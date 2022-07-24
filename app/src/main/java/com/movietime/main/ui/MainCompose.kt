package com.movietime.main.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import com.google.accompanist.insets.ProvideWindowInsets
import com.movietime.main.styles.AppTheme

@ExperimentalAnimationApi
@Composable
fun MainCompose(){
    ProvideWindowInsets {
        AppTheme {
            Main()
        }
    }
}