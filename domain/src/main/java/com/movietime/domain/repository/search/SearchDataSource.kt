package com.movietime.domain.repository.search

import com.movietime.domain.model.GenericPreview

interface SearchDataSource {
    suspend fun search(query: String, page: Int = 1): List<GenericPreview>
}