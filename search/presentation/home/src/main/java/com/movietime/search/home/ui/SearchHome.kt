package com.movietime.search.home.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.movietime.search.home.presentation.SearchViewModel
import com.movietime.views.PosterItemView
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun SearchHome(
    viewModel: SearchViewModel = hiltViewModel(),
    onMovieSelected: (id: Int) -> Unit,
    onTvShowSelected: (id: Int) -> Unit,
    onPersonSelected: (id: Int) -> Unit
) {
    val uiState by viewModel.searchUiState.collectAsStateWithLifecycle()

    SearchHome(
        viewModel = viewModel,
        uiState = uiState,
        onMovieSelected = onMovieSelected,
        onTvShowSelected = onTvShowSelected,
        onPersonSelected = onPersonSelected,
        onSearchThresholdReached = {viewModel.onSearchThresholdReached()},
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLifecycleComposeApi::class)
@Composable
fun SearchHome(
    viewModel: SearchViewModel,
    uiState: SearchViewModel.SearchUiState,
    onMovieSelected: (id: Int) -> Unit,
    onTvShowSelected: (id: Int) -> Unit,
    onPersonSelected: (id: Int) -> Unit,
    onSearchThresholdReached: () -> Unit,
) {
    var searchQuery by remember { mutableStateOf("") }
    viewModel.search(searchQuery)
    LazyColumn {
        item {
            OutlinedTextField(
                value = searchQuery,
                label = { Text("Search") },
                leadingIcon = { Image(Icons.Filled.Search, "Search") },
                onValueChange = {
                    searchQuery = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp, bottom = 16.dp, start = 8.dp, end = 8.dp)
            )
        }


        when (uiState) {
            SearchViewModel.SearchUiState.Loading -> {
                // Loading
            }
            SearchViewModel.SearchUiState.Error -> {
                // Error
                item {
                    Text("Error")
                }
            }
            is SearchViewModel.SearchUiState.Content -> {
                // Content
                itemsIndexed(items = uiState.searchResult) { index, posterItem ->
                    if (uiState.searchResult.size - index < 5) {
                        onSearchThresholdReached()
                    }
                    PosterItemView(
                        Modifier
                            .padding(8.dp)
                            .width(130.dp)
                            .aspectRatio(0.67f),
                        posterItem.posterUrl,
                        posterItem.rating,
                        {
//                            when (posterItem.type) {
//                                PosterItem.Type.MOVIE -> onMovieSelected(posterItem.id)
//                                PosterItem.Type.TV_SHOW -> onTvShowSelected(posterItem.id)
//                                PosterItem.Type.PERSON -> onPersonSelected(posterItem.id)
//                            }
                        }
                    )
                }
            }
        }

    }



}

