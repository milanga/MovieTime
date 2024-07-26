package com.movietime.search.home.presentation

class SearchListState(
    private val onRefresh: SearchListState.(String)->Unit,
    private val onLoadMore: SearchListState.()->Unit
) {
    private var loading = false

    fun refresh(query: String){
        loading = true
        onRefresh(query)
    }

    fun thresholdReached() {
        if (!loading){
            loading = true
            onLoadMore()
        }
    }

    fun finishLoading(){
        loading = false
    }
}
