package com.movietime.data.tmdb.mappers

import com.movietime.data.tmdb.model.TmdbVideo
import com.movietime.domain.model.Video


object VideoMapper {
    fun map(remoteVideo: TmdbVideo): Video =
        Video(
            remoteVideo.id,
            remoteVideo.iso6391,
            remoteVideo.iso31661,
            remoteVideo.key,
            remoteVideo.name,
            remoteVideo.site,
            remoteVideo.size,
            remoteVideo.type,
            remoteVideo.official,
            remoteVideo.publishedAt
        )
}