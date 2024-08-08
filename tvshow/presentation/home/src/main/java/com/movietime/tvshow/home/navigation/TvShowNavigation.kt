package com.movietime.tvshow.home.navigation

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
    }
    companion object {
        fun contains(route: String?): Boolean = route == HOME.route || route == "${DETAIL.route}/{${DETAIL.PARAM_TV_SHOW_ID}}"
    }
}

@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.tvShowsGraph(
    route: String,
    backNavigation: () -> Unit,
    navigateToRoute: (route: String) -> Unit
) {
    navigation(
        startDestination = TvShowDestinations.HOME.route,
        route = route
    ) {
        composable(TvShowDestinations.HOME.route) {
            TvShowHome{ tvShowId ->
                navigateToRoute("${TvShowDestinations.DETAIL.route}/$tvShowId")
            }
        }

        composable(
            route = "${TvShowDestinations.DETAIL.route}/{${TvShowDestinations.DETAIL.PARAM_TV_SHOW_ID}}",
            arguments = listOf(
                navArgument(TvShowDestinations.DETAIL.PARAM_TV_SHOW_ID) { type = NavType.IntType }
            )
        ) {
            TvShowDetailRoute(
                onTvShowSelected = { tvShowId ->
                    navigateToRoute("${TvShowDestinations.DETAIL.route}/$tvShowId")
                },
                onBackNavigation = backNavigation
            )
        }
    }
}