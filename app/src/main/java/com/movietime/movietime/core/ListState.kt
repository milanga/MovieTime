package com.movietime.movietime.core

class ListState(
    private val startPageIndex: Int = 1
) {
    private var currentPage = startPageIndex
    private var loading = false
    var onLoadPage: (pageIndex: Int)->Unit = {}

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