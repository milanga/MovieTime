package com.movietime.search.home.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.consumedWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.movietime.core.views.poster.PosterExtendedView
import com.movietime.core.views.poster.model.MediaType
import com.movietime.search.home.presentation.SearchViewModel
import com.movietime.core.views.poster.PosterItemView


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

@OptIn(ExperimentalMaterial3Api::class,
    ExperimentalLayoutApi::class
)
@Composable
fun SearchHome(
    viewModel: SearchViewModel,
    uiState: SearchViewModel.SearchUiState,
    onMovieSelected: (id: Int) -> Unit,
    onTvShowSelected: (id: Int) -> Unit,
    onPersonSelected: (id: Int) -> Unit,
    onSearchThresholdReached: () -> Unit,
) {
    Scaffold(
        topBar = {
            Surface(
                color = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp),
            ) { SearchField(viewModel)}
        },
        modifier = Modifier.fillMaxSize()
    ) { padding ->
        when (uiState) {
            SearchViewModel.SearchUiState.Loading -> {
                // Loading
            }
            SearchViewModel.SearchUiState.Error -> {
                // Error
                ErrorView()
            }
            is SearchViewModel.SearchUiState.Content -> {
                // Content
                SearchResultView(
                    uiState,
                    onSearchThresholdReached,
                    onMovieSelected,
                    onTvShowSelected,
                    onPersonSelected,
                    Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .consumedWindowInsets(padding)
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun SearchResultView(
    uiState: SearchViewModel.SearchUiState.Content,
    onSearchThresholdReached: () -> Unit,
    onMovieSelected: (id: Int) -> Unit,
    onTvShowSelected: (id: Int) -> Unit,
    onPersonSelected: (id: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        itemsIndexed(items = uiState.searchResult) { index, posterItem ->
            if (uiState.searchResult.size - index < 5) {
                onSearchThresholdReached()
            }
            PosterExtendedView(
                posterItem.posterUrl,
                posterItem.rating,
                posterItem.title,
                Modifier
                    .fillMaxWidth(),
                {
                    when (posterItem.mediaType) {
                        MediaType.Movie -> onMovieSelected(posterItem.id)
                        MediaType.TvShow -> onTvShowSelected(posterItem.id)
                        MediaType.Person -> onPersonSelected(posterItem.id)
                    }
                }
            )
        }
    }
}

@Composable
private fun ErrorView() {
    Box(contentAlignment = Alignment.Center) {
        Text("Error")
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun SearchField(viewModel: SearchViewModel) {
    var searchQuery by rememberSaveable { mutableStateOf("") }
    viewModel.search(searchQuery)
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

