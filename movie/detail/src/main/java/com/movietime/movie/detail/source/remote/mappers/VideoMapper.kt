package com.movietime.movie.detail.source.remote.mappers

import com.movietime.movie.detail.source.remote.model.RemoteVideo
import com.movietime.movie.domain.detail.Video

object VideoMapper {
    fun map(remoteVideo: RemoteVideo): Video =
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