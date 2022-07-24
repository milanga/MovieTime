package com.movietime.movie.data.remote.mappers

import com.movietime.movie.data.remote.model.RemoteVideo
import com.movietime.movie.model.model.Video


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