package com.movietime.movie.domain.repository

import com.movietime.movie.domain.interactors.MovieDetailRepository
import com.movietime.movie.domain.model.MovieDetail
import com.movietime.movie.domain.model.MoviePreview
import com.movietime.movie.domain.model.Video
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RemoteMovieDetailRepository @Inject constructor(
    private val remoteDetailDataSource: MovieDetailDataSource,
): MovieDetailRepository {
    override fun getMovieDetail(movieId: Int): Flow<MovieDetail> = flow{
        emit(remoteDetailDataSource.getMovieDetail(movieId))
    }
    override fun getMovieVideos(movieId: Int): Flow<List<Video>> = flow{
        emit(remoteDetailDataSource.getMovieVideos(movieId))
    }
    override fun getMovieRecommendations(movieId: Int, page: Int): Flow<List<MoviePreview>> = flow{
        emit(remoteDetailDataSource.getMovieRecommendations(movieId, page))
    }
}