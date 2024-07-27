package com.movietime.search.home.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.navigation.*
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.navigation
import com.movietime.movie.detail.ui.MovieDetailRoute
import com.movietime.search.home.ui.SearchHome

sealed class SearchDestinations(val route: String) {
    object HOME : SearchDestinations("search/home")
    object MOVIE_DETAIL : SearchDestinations("movies/detail") {
        const val PARAM_MOVIE_ID = "paramMovieId"
    }
    object TV_SHOW_DETAIL : SearchDestinations("tv/detail") {
        const val PARAM_TV_SHOW_ID = "paramTvShowId"
    }
    companion object {
        fun contains(route: String?): Boolean = route == HOME.route
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.searchGraph(
    route: String,
    backNavigation: () -> Unit,
    navigateToRoute: (route: String) -> Unit
) {
    val transitionTimeInMillis = 250
    navigation(
        startDestination = SearchDestinations.HOME.route,
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
        composable(SearchDestinations.HOME.route) {
            SearchHome(
                onMovieSelected = { movieId -> navigateToRoute("${SearchDestinations.MOVIE_DETAIL.route}/$movieId")},
                onTvShowSelected = { tvShowId -> navigateToRoute("${SearchDestinations.TV_SHOW_DETAIL.route}/$tvShowId")},
                onPersonSelected = { }
            )
        }

//        composable(
//            route = "${SearchDestinations.MOVIE_DETAIL.route}/{${SearchDestinations.MOVIE_DETAIL.PARAM_MOVIE_ID}}",
//            arguments = listOf(
//                navArgument(MovieDestinations.DETAIL.PARAM_MOVIE_ID) { type = NavType.IntType }
//            )
//        ) {
//            MovieDetailRoute(
//                onMovieSelected = { movieId ->
//                    navigateToRoute("${MovieDestinations.DETAIL.route}/$movieId")
//                },
//                onBackNavigation = backNavigation
//            )
//        }
    }
}