package com.movietime.domain.repository.movie

import com.movietime.domain.interactors.movie.MovieDetailRepository
import com.movietime.domain.model.MediaIds
import com.movietime.domain.model.MovieDetail
import com.movietime.domain.model.MoviePreview
import com.movietime.domain.model.Video
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RemoteMovieDetailRepository @Inject constructor(
    private val remoteDetailDataSource: MovieDetailDataSource,
    private val movieIdsDataSource: MovieIdsDataSource,
): MovieDetailRepository {
    override fun getMovieDetail(tmdbMovieId: Int): Flow<MovieDetail> {
        return flow { emit(remoteDetailDataSource.getMovieDetail(tmdbMovieId)) }
    }

    override fun getMovieIds(imdbMovieId: String): Flow<MediaIds> {
        return flow { emit(movieIdsDataSource.getMovieIds(imdbMovieId)) }
    }

    override  fun getMovieVideos(tmdbMovieId: Int): Flow<List<Video>> {
        return flow { emit(remoteDetailDataSource.getMovieVideos(tmdbMovieId)) }
    }

    override fun getRecommendations(tmdbMovieId: Int, page: Int): Flow<List<MoviePreview>> {
        return flow { emit(remoteDetailDataSource.getMovieRecommendations(tmdbMovieId, page)) }
    }
}