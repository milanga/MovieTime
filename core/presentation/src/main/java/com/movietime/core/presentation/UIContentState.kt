package com.movietime.core.presentation

sealed class UIContentState<T> {
    class Loading<T>: UIContentState<T>()
    data class ContentState<T>(val content: T): UIContentState<T>()
}