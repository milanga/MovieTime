package com.movietime.main

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import com.movietime.main.AppTheme
import com.google.accompanist.insets.ProvideWindowInsets
import com.movietime.main.main.Main

@ExperimentalAnimationApi
@Composable
fun MainCompose(){
    ProvideWindowInsets {
        AppTheme {
            Main()
        }
    }
}