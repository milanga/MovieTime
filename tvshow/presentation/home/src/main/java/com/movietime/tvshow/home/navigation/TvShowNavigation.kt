package com.movietime.tvshow.home.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.*
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.navigation
import com.movietime.tvshow.home.ui.TvShowHome

sealed class TvShowDestinations(val route: String) {
    object HOME : TvShowDestinations("tv/home")
    object DETAIL : TvShowDestinations("tv/detail") {
        const val PARAM_TV_SHOW_ID = "paramTvShowId"
    }
    companion object {
        fun contains(route: String?): Boolean = route == HOME.route || route == "${DETAIL.route}/{${DETAIL.PARAM_TV_SHOW_ID}}"
    }
}

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
fun NavGraphBuilder.tvShowsGraph(
    route: String,
    backNavigation: () -> Unit,
    navigateToRoute: (route: String) -> Unit
) {
    val transitionTimeInMillis = 250
    navigation(
        startDestination = TvShowDestinations.HOME.route,
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
        composable(TvShowDestinations.HOME.route) {
            TvShowHome{ tvShowId ->
                navigateToRoute("${TvShowDestinations.DETAIL.route}/$tvShowId")
            }
        }
    }
}