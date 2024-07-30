package com.movietime.domain.repository.movie

import com.movietime.domain.interactors.movie.MovieDetailRepository
import com.movietime.domain.interactors.movie.MovieDetailRepositoryFactory
import com.movietime.domain.model.MovieDetail
import com.movietime.domain.model.MediaIds
import com.movietime.domain.model.MoviePreview
import com.movietime.domain.model.Video
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow

class RemoteMovieDetailRepository @AssistedInject constructor(
    private val remoteDetailDataSource: MovieDetailDataSource,
    private val movieIdsDataSource: MovieIdsDataSource,
    @Assisted private val movieId: Int
): MovieDetailRepository {
    private var recommendationsPage = 1
    private val _recommendedMovies = MutableStateFlow<List<MoviePreview>>(emptyList())
    override val recommendedMovies: Flow<List<MoviePreview>> = _recommendedMovies

    private val _movieDetail = MutableSharedFlow<MovieDetail>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    override val movieDetail: Flow<MovieDetail> = _movieDetail

    private val _movieVideos = MutableSharedFlow<List<Video>>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    override val movieVideos: Flow<List<Video>> = _movieVideos

    private val _movieIds = MutableSharedFlow<MediaIds>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    override val movieIds: Flow<MediaIds> = _movieIds

    override suspend fun fetchMovieDetail() {
        _movieDetail.emit(remoteDetailDataSource.getMovieDetail(movieId))
    }

    override suspend fun fetchMovieIds(imdbId: String) {
        _movieIds.emit(movieIdsDataSource.getMovieIds(imdbId))
    }

    override suspend fun fetchMovieVideos() {
        _movieVideos.emit(remoteDetailDataSource.getMovieVideos(movieId))
    }

    override suspend fun refreshRecommendations() {
        recommendationsPage = 1
        _recommendedMovies.emit(remoteDetailDataSource.getMovieRecommendations(movieId, recommendationsPage))
    }
    override suspend fun fetchMoreRecommendations() {
        recommendationsPage++
        _recommendedMovies.emit(_recommendedMovies.value.plus(remoteDetailDataSource.getMovieRecommendations(movieId, recommendationsPage)))
    }
}

@AssistedFactory
interface RemoteMovieDetailRepositoryFactory:
    MovieDetailRepositoryFactory {
    override fun create(movieId: Int): RemoteMovieDetailRepository
}