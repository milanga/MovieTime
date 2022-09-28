package com.movietime.main.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import com.movietime.home.presentation.ui.Home
import com.movietime.main.styles.AppTheme

@ExperimentalAnimationApi
@Composable
fun MainCompose(){
    AppTheme {
        Home()
    }
}