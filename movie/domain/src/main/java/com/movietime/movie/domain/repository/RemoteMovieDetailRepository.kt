package com.movietime.movie.domain.repository

import com.movietime.movie.domain.interactors.MovieDetailRepository
import com.movietime.movie.domain.model.MovieDetail
import com.movietime.movie.domain.model.MoviePreview
import com.movietime.movie.domain.model.Video
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class RemoteMovieDetailRepository @Inject constructor(
    private val remoteDetailDataSource: MovieDetailDataSource,
): MovieDetailRepository {
    private var recommendationsPage = 1
    private val _recommendedMovies = MutableStateFlow<List<MoviePreview>>(emptyList())
    override val recommendedMovies: Flow<List<MoviePreview>> = _recommendedMovies

    private val _movieDetail = MutableSharedFlow<MovieDetail>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    override val movieDetail: Flow<MovieDetail> = _movieDetail

    private val _movieVideos = MutableSharedFlow<List<Video>>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    override val movieVideos: Flow<List<Video>> = _movieVideos

    override suspend fun fetchMovieDetail(movieId: Int) {
        _movieDetail.emit(remoteDetailDataSource.getMovieDetail(movieId))
    }
    override suspend fun fetchMovieVideos(movieId: Int) {
        _movieVideos.emit(remoteDetailDataSource.getMovieVideos(movieId))
    }

    override suspend fun refreshRecommendations(movieId: Int) {
        recommendationsPage = 1
        _recommendedMovies.emit(remoteDetailDataSource.getMovieRecommendations(movieId, recommendationsPage))
    }
    override suspend fun fetchMoreRecommendations(movieId: Int) {
        recommendationsPage++
        _recommendedMovies.emit(_recommendedMovies.value.plus(remoteDetailDataSource.getMovieRecommendations(movieId, recommendationsPage)))
    }
    //todo mover id al constructor
}