package com.example.movietime

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.compose.AppTheme
import com.example.movietime.home.Home
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