package com.movietime.core.presentation

class ListState(
    private val onRefresh: ListState.()->Unit,
    private val onLoadMore: ListState.()->Unit
) {
    private var loading = false

    fun refresh(){
        loading = true
        onRefresh()
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