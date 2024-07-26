package com.movietime.domain.interactors.search

import com.movietime.domain.model.GenericPreview
import javax.inject.Inject

class SearchUseCase @Inject constructor(
    private val searchRepository: SearchRepository
) {
    private var lastSearchedQuery = ""
    private var lastSearchedPage = 1

    suspend fun search(query: String): List<GenericPreview> {
        lastSearchedPage = 1
        lastSearchedQuery = query
        return searchWithCurrentConfig()
    }

    suspend fun searchMore(): List<GenericPreview> {
        lastSearchedPage++
        return searchWithCurrentConfig()
    }

    private suspend fun searchWithCurrentConfig() =  searchRepository.search(lastSearchedQuery, lastSearchedPage)
}