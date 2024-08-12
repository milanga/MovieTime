package com.movietime.movie.home.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.*
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.movietime.movie.detail.ui.MovieDetailRoute
import com.movietime.movie.home.ui.MovieHome

sealed class MovieDestinations(val route: String) {
    object HOME : MovieDestinations("movies/home")
    object DETAIL : MovieDestinations("movies/detail") {
        const val PARAM_MOVIE_ID = "paramMovieId"
        const val PARAM_ORIGIN = "paramOrigin"
    }
    companion object {
        fun contains(route: String?): Boolean = route == HOME.route || route == "${DETAIL.route}/{${DETAIL.PARAM_MOVIE_ID}}"
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.moviesGraph(
    transitionScope: SharedTransitionScope,
    route: String,
    backNavigation: () -> Unit,
    navigateToRoute: (route: String) -> Unit
) {
    navigation(
        startDestination = MovieDestinations.HOME.route,
        route = route
    ) {
        composable(MovieDestinations.HOME.route) {
            MovieHome(
                transitionScope = transitionScope,
                animatedContentScope = this@composable
            ) { movieId, origin ->
                navigateToRoute("${MovieDestinations.DETAIL.route}/$movieId/$origin")
            }
        }

        composable(
            route = "${MovieDestinations.DETAIL.route}/{${MovieDestinations.DETAIL.PARAM_MOVIE_ID}}/{${MovieDestinations.DETAIL.PARAM_ORIGIN}}",
            arguments = listOf(
                navArgument(MovieDestinations.DETAIL.PARAM_MOVIE_ID) { type = NavType.IntType },
                navArgument(MovieDestinations.DETAIL.PARAM_ORIGIN) { type = NavType.StringType }
            )
        ) { backStackEntry ->
            MovieDetailRoute(
                transitionScope = transitionScope,
                animatedContentScope = this@composable,
                onMovieSelected = { movieId ->
                    navigateToRoute("${MovieDestinations.DETAIL.route}/$movieId")
                },
                onBackNavigation = backNavigation
            )
        }
    }
}