package com.movietime.tvshow.home.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.movietime.tvshow.detail.ui.TvShowDetailRoute
import com.movietime.tvshow.home.ui.TvShowHome

sealed class TvShowDestinations(val route: String) {
    object HOME : TvShowDestinations("tv/home")
    object DETAIL : TvShowDestinations("tv/detail") {
        const val PARAM_TV_SHOW_ID = "paramTvShowId"
        const val PARAM_ORIGIN = "paramOrigin"
    }
    companion object {
        fun contains(route: String?): Boolean = route == HOME.route || route == "${DETAIL.route}/{${DETAIL.PARAM_TV_SHOW_ID}}"
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.tvShowsGraph(
    transitionScope: SharedTransitionScope,
    route: String,
    backNavigation: () -> Unit,
    navigateToRoute: (route: String) -> Unit
) {
    navigation(
        startDestination = TvShowDestinations.HOME.route,
        route = route
    ) {
        composable(TvShowDestinations.HOME.route) {
            TvShowHome(
                transitionScope = transitionScope,
                animatedContentScope = this@composable,
            ){ tvShowId, origin ->
                navigateToRoute("${TvShowDestinations.DETAIL.route}/$tvShowId/$origin")
            }
        }

        composable(
            route = "${TvShowDestinations.DETAIL.route}/{${TvShowDestinations.DETAIL.PARAM_TV_SHOW_ID}}/{${TvShowDestinations.DETAIL.PARAM_ORIGIN}}",
            arguments = listOf(
                navArgument(TvShowDestinations.DETAIL.PARAM_TV_SHOW_ID) { type = NavType.IntType },
                navArgument(TvShowDestinations.DETAIL.PARAM_ORIGIN) { type = NavType.StringType }
            )
        ) {
            TvShowDetailRoute(
                transitionScope = transitionScope,
                animatedContentScope = this@composable,
                onTvShowSelected = { tvShowId, origin ->
                    navigateToRoute("${TvShowDestinations.DETAIL.route}/$tvShowId/$origin")
                },
                onBackNavigation = backNavigation
            )
        }
    }
}