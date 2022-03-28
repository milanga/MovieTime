package com.milanga.movietime.home

import androidx.annotation.StringRes
import androidx.compose.animation.*
import androidx.compose.animation.AnimatedContentScope.SlideDirection.Companion.Left
import androidx.compose.animation.AnimatedContentScope.SlideDirection.Companion.Right
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.milanga.movietime.R
import com.milanga.movietime.components.MovieTimeNavigationBar
import com.milanga.movietime.components.MovieTimeNavigationBarItem
import com.milanga.movietime.components.MovieTimeScaffold
import com.milanga.movietime.home.MainDestinations.HOME
import com.milanga.movietime.movies.MovieDetail
import com.milanga.movietime.movies.Movies
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.navigation
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

sealed class NavSection(val route: String, @StringRes val title: Int) {
    object Movies : NavSection("home/movies", R.string.movies)
    object Series : NavSection("home/series", R.string.series)
    object Search : NavSection("home/search", R.string.search)
}

sealed class MainDestinations(val route: String) {
    object HOME : MainDestinations("home")
    object DETAIL : MainDestinations("detail") {
        const val PARAM_MOVIE_ID = "paramMovieId"
    }
//    data class DETAIL(val paramMovieId: String = "paramMovieId"): MainDestinations("detail")
}

val homeSections = listOf(NavSection.Movies, NavSection.Series, NavSection.Search)

@ExperimentalAnimationApi
@OptIn(ExperimentalMaterial3Api::class)
@Preview()
@Composable
fun Home() {
    val navController = rememberAnimatedNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentSectionRoute = navBackStackEntry?.destination?.route
        ?: HOME.route
    AnimatedNavHost(navController, startDestination = HOME.route) {
        navigation(
            route = HOME.route,
            startDestination = NavSection.Movies.route,
            exitTransition = {
                if(targetState.destination.route !in homeSections.map { it.route }) {
                    slideOutOfContainer(Left, tween(500))
                } else {
                    fadeOut(animationSpec = tween(700))
                } },
            popEnterTransition = {
                if(initialState.destination.route !in homeSections.map { it.route }) {
                    slideIntoContainer(Right, tween(500))
                } else {
                    fadeIn(animationSpec = tween(700))
                }
                                 },
        ) {
            composable(NavSection.Movies.route) { from ->
                Movies(
                    onMovieSelected = { movieId ->
                        navController.navigate("${MainDestinations.DETAIL.route}/$movieId")
                    },
                    navSections = homeSections,
                    onSectionSelected = { selectedSection ->
                        if (selectedSection.route != currentSectionRoute) {
                            navController.navigate(selectedSection.route) {
                                // Pop up to the start destination of the graph to
                                // avoid building up a large stack of destinations
                                // on the back stack as users select items
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                // Avoid multiple copies of the same destination when
                                // reselecting the same item
                                launchSingleTop = true
                                // Restore state when reselecting a previously selected item
                                restoreState = true
                            }
                        }
                    }
                )
            }
            composable(NavSection.Series.route) {
                EmptyScreenWithNav(
                    homeSections,
                    selectedSection = NavSection.Series,
                    onSectionSelected = { selectedSection ->
                        if (selectedSection.route != currentSectionRoute) {
                            navController.navigate(selectedSection.route) {
                                // Pop up to the start destination of the graph to
                                // avoid building up a large stack of destinations
                                // on the back stack as users select items
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                // Avoid multiple copies of the same destination when
                                // reselecting the same item
                                launchSingleTop = true
                                // Restore state when reselecting a previously selected item
                                restoreState = true
                            }
                        }
                    }
                )
            }
            composable(NavSection.Search.route) {
                EmptyScreenWithNav(
                    homeSections,
                    selectedSection = NavSection.Search,
                    onSectionSelected = { selectedSection ->
                        if (selectedSection.route != currentSectionRoute) {
                            navController.navigate(selectedSection.route) {
                                // Pop up to the start destination of the graph to
                                // avoid building up a large stack of destinations
                                // on the back stack as users select items
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                // Avoid multiple copies of the same destination when
                                // reselecting the same item
                                launchSingleTop = true
                                // Restore state when reselecting a previously selected item
                                restoreState = true
                            }
                        }
                    }
                )
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

        ) { backStackEntry: NavBackStackEntry ->
            MovieDetail(
                onMovieSelected = { movieId ->
                    navController.navigate("${MainDestinations.DETAIL.route}/$movieId")
                },
                onBackNavigation = { navController.popBackStack() }
            )
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun EmptyScreenWithNav(
    navSections: List<NavSection>,
    onSectionSelected: (NavSection) -> Unit,
    selectedSection: NavSection
) {
    Scaffold(bottomBar = {
        val bottomSysBarsHeight =
            with(LocalDensity.current) { LocalWindowInsets.current.systemBars.bottom.toDp() }
        MovieTimeNavigationBar(modifier = Modifier.height(80.dp + bottomSysBarsHeight)) {
            navSections.forEach { navSection ->
                MovieTimeNavigationBarItem(
                    selected = navSection == selectedSection,
                    onClick = { onSectionSelected(navSection) },
                    icon = { Icon(imageVector = Icons.Filled.Movie, contentDescription = null) },
                    label = { Text(stringResource(navSection.title)) },
                    alwaysShowLabel = true,
                    modifier = Modifier.padding(bottom = bottomSysBarsHeight)
                )
            }
        }
    }) {

    }
}