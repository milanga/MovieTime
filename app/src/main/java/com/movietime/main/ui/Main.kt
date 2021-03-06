package com.movietime.main.ui

import androidx.compose.animation.AnimatedContentScope.SlideDirection.Companion.Left
import androidx.compose.animation.AnimatedContentScope.SlideDirection.Companion.Right
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.movietime.home.presentation.ui.Home
import com.movietime.movie.detail.ui.MovieDetailView


sealed class MainDestinations(val route: String) {
    object HOME : MainDestinations("home")
    object DETAIL : MainDestinations("detail") {
        const val PARAM_MOVIE_ID = "paramMovieId"
    }
}

@ExperimentalAnimationApi
@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun Main() {
    val navController = rememberAnimatedNavController()
    AnimatedNavHost(navController, startDestination = MainDestinations.HOME.route) {
        composable(MainDestinations.HOME.route) {
            Home{movieId ->
                navController.navigate("${MainDestinations.DETAIL.route}/$movieId")
            }
        }

        composable(
            route = "${MainDestinations.DETAIL.route}/{${MainDestinations.DETAIL.PARAM_MOVIE_ID}}",
            arguments = listOf(
                navArgument(MainDestinations.DETAIL.PARAM_MOVIE_ID) { type = NavType.IntType }
            ),
            enterTransition = { slideIntoContainer(Left, tween(500)) },
            exitTransition = { slideOutOfContainer(Left, tween(500)) },
            popEnterTransition = { slideIntoContainer(Right, tween(500)) },
            popExitTransition = { slideOutOfContainer(Right, tween(500)) }

        ) {
            MovieDetailView(
                onMovieSelected = { movieId ->
                    navController.navigate("${MainDestinations.DETAIL.route}/$movieId")
                },
                onBackNavigation = { navController.popBackStack() }
            )
        }
    }
}

