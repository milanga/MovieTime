package com.movietime.home.presentation.ui

import androidx.annotation.StringRes
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.navigation
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.movietime.home.presentation.R
import com.movietime.movie.home.navigation.moviesGraph

sealed class NavSection(val route: String, @StringRes val title: Int, val icon: ImageVector) {
    companion object{
        const val HomeSectionsRoute = "home/sections"
    }
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
fun Home(){
    val navController = rememberAnimatedNavController()
    Scaffold(
        bottomBar = {
            BottomBar(navController)
        },
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { contentPadding ->
        AnimatedNavHost(navController, startDestination = NavSection.Movies.route, modifier = Modifier.padding(contentPadding)) {
            moviesGraph(NavSection.Movies.route, {navController.popBackStack()}){route ->  navController.navigate(route)}

            navigation(route = NavSection.Series.route, startDestination = "series/home"){
                composable("series/home") {
                    Surface(Modifier.fillMaxSize()) {}
                }
            }

            navigation(route = NavSection.Search.route, startDestination = "search/home"){
                composable("search/home") {
                    Surface(Modifier.fillMaxSize()) {}
                }
            }


        }
    }
}

@Composable
private fun BottomBar(
    navController: NavController
) {
    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        homeSections.forEach { navSection ->
            NavigationBarItem(
                selected = currentDestination?.hierarchy?.any { it.route == navSection.route } == true,
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
            )
        }
    }
}

