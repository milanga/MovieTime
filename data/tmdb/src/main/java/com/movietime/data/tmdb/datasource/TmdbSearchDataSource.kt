package com.movietime.data.tmdb.datasource

import com.movietime.data.tmdb.mappers.GenericPreviewMapper
import com.movietime.data.tmdb.model.TmdbSearchResponse
import com.movietime.data.tmdb.service.SearchService
import com.movietime.domain.model.GenericPreview
import com.movietime.domain.repository.search.SearchDataSource
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named
import kotlin.coroutines.CoroutineContext

class TmdbSearchDataSource @Inject constructor(
    private val searchService: SearchService,
    private val mapper: GenericPreviewMapper,
    @Named("io_dispatcher") private val coroutineContext: CoroutineContext
) : SearchDataSource {
    override suspend fun search(query: String, page: Int): List<GenericPreview> = withContext(coroutineContext) { getListOfSearch(searchService.search(query, page))
    }

    private fun getListOfSearch(tmdbSearchResponse: TmdbSearchResponse): List<GenericPreview> =
        tmdbSearchResponse.results.map { tmdbGenericPreview ->
            mapper.map(
                tmdbGenericPreview
            )
        }

}