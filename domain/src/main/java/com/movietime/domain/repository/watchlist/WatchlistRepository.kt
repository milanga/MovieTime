package com.movietime.domain.repository.watchlist

import com.movietime.domain.model.MediaIds
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import javax.inject.Named

class WatchlistRepository @Inject constructor(
    @Named("TRAKT_WATCHLIST_DATA_SOURCE")
    private val traktWatchlistDataSource: WatchlistDataSource,
    @Named("MEMORY_WATCHLIST_DATA_SOURCE")
    private val memoryWatchlistDataSource: WatchlistDataSource
) {
    companion object {
        private const val DEBOUNCE_TIME = 2000L
    }

    private val repositoryEvents = MutableStateFlow<WatchlistRepositoryEvent>(WatchlistRepositoryEvent.None)

    /**
     * execute events debounced to avoid multiple calls on a short period of time
     * */
    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    private suspend fun executeRemoteEventsDebounced() {
        repositoryEvents
            .debounce(DEBOUNCE_TIME)
            .onEach { event ->
                when (event) {
                    is WatchlistRepositoryEvent.AddMovie -> traktWatchlistDataSource.addMovieToWatchlist(
                        event.tmdbID
                    )

                    is WatchlistRepositoryEvent.RemoveMovie -> traktWatchlistDataSource.removeMovieFromWatchlist(
                        event.tmdbID
                    )

                    is WatchlistRepositoryEvent.AddTvShow -> traktWatchlistDataSource.addTvShowToWatchlist(
                        event.tmdbID
                    )

                    is WatchlistRepositoryEvent.RemoveTvShow -> traktWatchlistDataSource.removeTvShowFromWatchlist(
                        event.tmdbID
                    )

                    WatchlistRepositoryEvent.None -> {}
                }
            }.flatMapLatest { event ->
                when (event) {
                    is WatchlistRepositoryEvent.AddMovie,
                    is WatchlistRepositoryEvent.RemoveMovie -> getMovieFromRemoteAndStoreInMemory()

                    is WatchlistRepositoryEvent.AddTvShow,
                    is WatchlistRepositoryEvent.RemoveTvShow -> getTvShowFromRemoteAndStoreInMemory()

                    WatchlistRepositoryEvent.None -> flowOf(emptyList())
                }
            }.collect()
    }

    suspend fun addMovieToWatchlist(tmdbID: Int) {
        repositoryEvents.value = WatchlistRepositoryEvent.AddMovie(tmdbID)
        memoryWatchlistDataSource.addMovieToWatchlist(tmdbID)
        executeRemoteEventsDebounced()
    }

    suspend fun addTvShowToWatchlist(tmdbID: Int) {
        repositoryEvents.value = WatchlistRepositoryEvent.AddTvShow(tmdbID)
        memoryWatchlistDataSource.addTvShowToWatchlist(tmdbID)
        executeRemoteEventsDebounced()
    }

    suspend fun removeMovieFromWatchlist(tmdbID: Int) {
        repositoryEvents.value = WatchlistRepositoryEvent.RemoveMovie(tmdbID)
        memoryWatchlistDataSource.removeMovieFromWatchlist(tmdbID)
        executeRemoteEventsDebounced()
    }

    suspend fun removeTvShowFromWatchlist(tmdbID: Int) {
        repositoryEvents.value = WatchlistRepositoryEvent.RemoveTvShow(tmdbID)
        memoryWatchlistDataSource.removeTvShowFromWatchlist(tmdbID)
        executeRemoteEventsDebounced()
    }

    fun getMovieWatchlistIds(): Flow<List<MediaIds>> {
        return memoryWatchlistDataSource.getMovieWatchlistIds().flatMapConcat {
            if (it.isEmpty()){
                getMovieFromRemoteAndStoreInMemory()
            } else {
                flowOf(it)
            }
        }
    }

    fun getTvShowWatchlistIds(): Flow<List<MediaIds>> {
        return memoryWatchlistDataSource.getTvShowWatchlistIds().flatMapConcat {
                if (it.isEmpty()){
                    getTvShowFromRemoteAndStoreInMemory()
                } else {
                    flowOf(it)
                }
            }
    }

    private fun getTvShowFromRemoteAndStoreInMemory() = traktWatchlistDataSource.getTvShowWatchlistIds().onEach { remoteValue ->
        memoryWatchlistDataSource.setTvShowWatchlistIds(remoteValue)
    }

    private fun getMovieFromRemoteAndStoreInMemory() = traktWatchlistDataSource.getMovieWatchlistIds().onEach { remoteValue ->
        memoryWatchlistDataSource.setMovieWatchlistIds(remoteValue)
    }
}

private sealed interface WatchlistRepositoryEvent {
    object None : WatchlistRepositoryEvent
    data class AddMovie(val tmdbID: Int) : WatchlistRepositoryEvent
    data class RemoveMovie(val tmdbID: Int) : WatchlistRepositoryEvent
    data class AddTvShow(val tmdbID: Int) : WatchlistRepositoryEvent
    data class RemoveTvShow(val tmdbID: Int) : WatchlistRepositoryEvent
}