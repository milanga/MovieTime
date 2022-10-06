package com.movietime.movie.home.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.*
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.navigation
import com.movietime.movie.detail.ui.MovieDetailRoute
import com.movietime.movie.home.ui.MovieHome

sealed class MovieDestinations(val route: String) {
    object HOME : MovieDestinations("movies/home")
    object DETAIL : MovieDestinations("movies/detail") {
        const val PARAM_MOVIE_ID = "paramMovieId"
    }
    companion object {
        fun contains(route: String?): Boolean = route == HOME.route || route == "${DETAIL.route}/{${DETAIL.PARAM_MOVIE_ID}}"
    }
}

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
fun NavGraphBuilder.moviesGraph(
    route: String,
    backNavigation: () -> Unit,
    navigateToRoute: (route: String) -> Unit
) {
    val transitionTimeInMillis = 250
    navigation(
        startDestination = MovieDestinations.HOME.route,
        route = route,
        enterTransition = {
            if(initialState.destination.parent?.id == targetState.destination.parent?.id){
                fadeIn(animationSpec = tween(transitionTimeInMillis)) + slideIntoContainer(AnimatedContentScope.SlideDirection.Start, tween(
                    transitionTimeInMillis
                ))
            } else {
                fadeIn(animationSpec = tween(transitionTimeInMillis))
            }
        },
        exitTransition = {
            if(initialState.destination.parent?.id == targetState.destination.parent?.id){
                fadeOut(animationSpec = tween(transitionTimeInMillis)) + slideOutOfContainer(AnimatedContentScope.SlideDirection.Start, tween(
                    transitionTimeInMillis
                ))
            } else {
                fadeOut(animationSpec = tween(transitionTimeInMillis))
            }
        },
        popEnterTransition = {
            if(initialState.destination.parent?.id == targetState.destination.parent?.id){
                fadeIn(animationSpec = tween(transitionTimeInMillis)) + slideIntoContainer(AnimatedContentScope.SlideDirection.End, tween(
                    transitionTimeInMillis
                ))
            } else {
                fadeIn(animationSpec = tween(transitionTimeInMillis))
            }
        },
        popExitTransition = {
            fadeOut(animationSpec = tween(transitionTimeInMillis)) + slideOutOfContainer(AnimatedContentScope.SlideDirection.End, tween(
                transitionTimeInMillis
            ))
        }
    ) {
        composable(MovieDestinations.HOME.route) {
            MovieHome{ movieId ->
                navigateToRoute("${MovieDestinations.DETAIL.route}/$movieId")
            }
        }

        composable(
            route = "${MovieDestinations.DETAIL.route}/{${MovieDestinations.DETAIL.PARAM_MOVIE_ID}}",
            arguments = listOf(
                navArgument(MovieDestinations.DETAIL.PARAM_MOVIE_ID) { type = NavType.IntType }
            )
        ) {
            MovieDetailRoute(
                onMovieSelected = { movieId ->
                    navigateToRoute("${MovieDestinations.DETAIL.route}/$movieId")
                },
                onBackNavigation = backNavigation
            )
        }
    }
}