package com.movietime.domain.repository.search

import com.movietime.domain.interactors.search.SearchRepository
import com.movietime.domain.interactors.tvshow.TvShowsRepository
import com.movietime.domain.model.GenericPreview
import com.movietime.domain.model.TvShowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class TmdbSearchRepository @Inject constructor(
    private val remoteDataSource: SearchDataSource,
): SearchRepository {
    override suspend fun search(query: String, page: Int): List<GenericPreview> {
        return remoteDataSource.search(query, page)
    }
}