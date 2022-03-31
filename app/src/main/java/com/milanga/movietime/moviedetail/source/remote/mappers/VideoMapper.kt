package com.milanga.movietime.moviedetail.source.remote.mappers

import com.milanga.movietime.moviedetail.domain.Company
import com.milanga.movietime.moviedetail.domain.Video
import com.milanga.movietime.moviedetail.source.remote.model.RemoteCompany
import com.milanga.movietime.moviedetail.source.remote.model.RemoteVideo

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