package com.movietime.core.presentation

class ListState(
    private val startPageIndex: Int = 1,
    private val onLoadPage: ListState.(pageIndex: Int)->Unit = {}
) {
    private var currentPage = startPageIndex
    private var loading = false

    fun refresh(){
        currentPage = startPageIndex
        loading = true
        onLoadPage(currentPage)
    }

    fun thresholdReached() {
        if (!loading){
            loading = true
            currentPage++
            onLoadPage(currentPage)
        }
    }

    fun finishLoading(){
        loading = false
    }
}