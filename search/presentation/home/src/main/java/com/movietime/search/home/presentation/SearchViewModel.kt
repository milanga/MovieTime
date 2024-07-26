package com.movietime.search.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.movietime.core.views.poster.model.PosterItem
import com.movietime.domain.interactors.search.SearchUseCase
import com.movietime.domain.model.GenericPreview
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchUseCase: SearchUseCase,
): ViewModel() {
    sealed interface SearchUiState{
        object Loading: SearchUiState
        object Error: SearchUiState
        data class Content(
            val searchResult: List<PosterItem>
        ): SearchUiState
    }

    private val currentSearchList = mutableListOf<PosterItem>()
    private val _searchUiState = MutableStateFlow<SearchUiState>(SearchUiState.Content(currentSearchList))
    val searchUiState: StateFlow<SearchUiState> = _searchUiState

    private val searchResultListState = SearchListState(
        { query ->
            currentSearchList.clear()
            _searchUiState.value = SearchUiState.Loading
            fetchList(this) { searchUseCase.search(query) }
        }, {
            fetchList(this) { searchUseCase.searchMore() }
        }
    )

    private fun fetchList(listState: SearchListState, fetchFunction: suspend ()->List<GenericPreview>){
        viewModelScope.launch {
            try {
                currentSearchList.addAll(fetchFunction().map(GenericPreview::toPosterItem))
                _searchUiState.value = SearchUiState.Content(currentSearchList)
            } catch (e: Exception) {
                e.printStackTrace()
                _searchUiState.value = SearchUiState.Error
            } finally {
                listState.finishLoading()
            }
        }
    }

    private val asd: MutableStateFlow<String> by lazy {
        val value = MutableStateFlow("")
        viewModelScope.launch {
            value.debounce(400).collect{searchResultListState.refresh(it)}
        }
        value
    }

    fun search(query: String){
        asd.value = query
    }

    fun onSearchThresholdReached(){
        searchResultListState.thresholdReached()
    }
}