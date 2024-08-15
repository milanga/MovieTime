package com.movietime.search.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.movietime.search.home.ui.SearchHome

private const val SEARCH_ORIGIN = "search"

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

fun NavGraphBuilder.searchGraph(
    route: String,
    backNavigation: () -> Unit,
    navigateToRoute: (route: String) -> Unit
) {
    navigation(
        startDestination = SearchDestinations.HOME.route,
        route = route
    ) {
        composable(SearchDestinations.HOME.route) {
            SearchHome(
                onMovieSelected = { movieId -> navigateToRoute("${SearchDestinations.MOVIE_DETAIL.route}/$movieId/$SEARCH_ORIGIN")},
                onTvShowSelected = { tvShowId -> navigateToRoute("${SearchDestinations.TV_SHOW_DETAIL.route}/$tvShowId/$SEARCH_ORIGIN")},
                onPersonSelected = { }
            )
        }
    }
}