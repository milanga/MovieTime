package com.movietime.search.home.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.navigation.*
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.navigation
import com.movietime.search.home.ui.SearchHome

sealed class SearchDestinations(val route: String) {
    object HOME : SearchDestinations("search/home")

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
                onMovieSelected= {},
                onTvShowSelected = {},
                onPersonSelected = {}
            )
        }
    }
}