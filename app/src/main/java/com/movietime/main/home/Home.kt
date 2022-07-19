package com.movietime.main.home

import android.annotation.SuppressLint
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.navigation
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.movietime.movie.home.ui.MovieHome
import com.movietime.views.components.MovieTimeNavigationBar
import com.movietime.views.components.MovieTimeNavigationBarItem
import com.movietime.main.R

const val HomeSectionsRoute = "home/sections"

sealed class NavSection(val route: String, @StringRes val title: Int, val icon: ImageVector) {
    object Movies : NavSection("$HomeSectionsRoute/movies", R.string.movies, Icons.Filled.Movie)
    object Series : NavSection("$HomeSectionsRoute/series", R.string.series, Icons.Filled.Tv)
    object Search : NavSection("$HomeSectionsRoute/search", R.string.profile, Icons.Filled.Person)
}

val homeSections = listOf(NavSection.Movies, NavSection.Series, NavSection.Search)

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalAnimationApi::class
)
@Composable
fun Home(
    onMovieSelected: (movieId: Int)->Unit
){
    val navController = rememberAnimatedNavController()
    Scaffold(
        bottomBar = {
            BottomBar(navController)
        }
    ) { contentPadding ->
        AnimatedNavHost(navController, startDestination = HomeSectionsRoute) {
            navigation(
                route = HomeSectionsRoute,
                startDestination = NavSection.Movies.route,
                exitTransition = {
                    if (targetState.destination.route !in homeSections.map { it.route }) {
                        slideOutOfContainer(AnimatedContentScope.SlideDirection.Left, tween(500))
                    } else {
                        fadeOut(animationSpec = tween(700))
                    }
                },
                popEnterTransition = {
                    if (initialState.destination.route !in homeSections.map { it.route }) {
                        slideIntoContainer(AnimatedContentScope.SlideDirection.Right, tween(500))
                    } else {
                        fadeIn(animationSpec = tween(700))
                    }
                },
            ) {
                composable(NavSection.Movies.route) {
                    MovieHome(onMovieSelected = onMovieSelected, contentPadding = contentPadding)
                }

                composable(NavSection.Series.route) {
                    Surface(Modifier.fillMaxSize()) {}
                }

                composable(NavSection.Search.route) {
                    Surface(Modifier.fillMaxSize()) {}
                }

            }
        }
    }
}

@Composable
private fun BottomBar(
    navController: NavHostController
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentSectionRoute = navBackStackEntry?.destination?.route ?: NavSection.Movies.route
    val bottomSysBarsHeight = with(LocalDensity.current) { LocalWindowInsets.current.systemBars.bottom.toDp() }

    MovieTimeNavigationBar(
        modifier = Modifier
            .height(80.dp + bottomSysBarsHeight)
    ) {
        homeSections.forEach { navSection ->
            MovieTimeNavigationBarItem(
                selected = currentSectionRoute == navSection.route,
                onClick = {
                    navController.navigate(navSection.route) {
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
                },
                icon = { Icon(imageVector = navSection.icon, contentDescription = null) },
                label = { Text(stringResource(navSection.title)) },
                alwaysShowLabel = true,
                modifier = Modifier.padding(bottom = bottomSysBarsHeight)
            )
        }
    }
}

