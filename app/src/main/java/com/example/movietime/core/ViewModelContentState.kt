package com.example.movietime.core

import java.lang.RuntimeException

sealed class ViewModelContentState<T> {
    class Loading<T>: ViewModelContentState<T>()
    data class ContentState<T>(val content: T): ViewModelContentState<T>()
    data class Error<T>(val errorMessage: String): ViewModelContentState<T>()

    fun toUiContentState(): UIContentState<T> {
        return when(this){
            is Loading -> UIContentState.Loading()
            is ContentState -> UIContentState.ContentState(content)
            is Error -> throw RuntimeException("Can't map error to ui content state")
        }
    }
}