package com.movietime.domain.interactors.search

import com.movietime.domain.model.GenericPreview

interface SearchRepository {
    suspend fun search(query: String, page: Int = 1): List<GenericPreview>
}