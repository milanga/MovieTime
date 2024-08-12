package com.movietime.home.presentation.ui

import androidx.annotation.StringRes
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.movietime.auth.AuthDialog
import com.movietime.home.presentation.R
import com.movietime.movie.home.navigation.moviesGraph
import com.movietime.search.home.navigation.searchGraph
import com.movietime.tvshow.home.navigation.tvShowsGraph

sealed class NavSection(val route: String, @StringRes val title: Int, val icon: ImageVector) {
    companion object{
        const val HomeSectionsRoute = "home/sections"
    }
    object Movies : NavSection("$HomeSectionsRoute/movies", R.string.movies, Icons.Filled.Movie)
    object TvShows : NavSection("$HomeSectionsRoute/tvShows", R.string.tv_shows, Icons.Filled.Tv)
    object Search : NavSection("$HomeSectionsRoute/search", R.string.search, Icons.Filled.Search)
    object Profile : NavSection("$HomeSectionsRoute/profile", R.string.profile, Icons.Filled.Person)
}

val homeSections = listOf(NavSection.Movies, NavSection.TvShows, NavSection.Search)

@OptIn(
    ExperimentalSharedTransitionApi::class
)
@Composable
fun Home(){
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomBar(navController)
        },
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { contentPadding ->
        SharedTransitionLayout {
            NavHost(
                navController,
                startDestination = NavSection.Movies.route,
                modifier = Modifier
                    .padding(contentPadding)
                    .consumeWindowInsets(contentPadding)
            ) {
                moviesGraph(
                    this@SharedTransitionLayout,
                    NavSection.Movies.route,
                    { navController.popBackStack() }) { route -> navController.navigate(route) }

                tvShowsGraph(
                    this@SharedTransitionLayout,
                    NavSection.TvShows.route,
                    { navController.popBackStack() }) { route -> navController.navigate(route) }

                searchGraph(
                    NavSection.Search.route,
                    { navController.popBackStack() }) { route -> navController.navigate(route) }
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

        val showDialog = remember { mutableStateOf(false) }
        if (showDialog.value) {
            AuthDialog{ showDialog.value = false }
        }
        NavigationBarItem(
            selected = false,
            onClick = { showDialog.value = true },
            icon = { Icon(imageVector = Icons.Filled.Person, contentDescription = null) },
            label = { Text(stringResource(R.string.profile)) },
            alwaysShowLabel = true,
        )
    }
}

