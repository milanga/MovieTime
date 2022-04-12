package com.movietime.movietime.moviedetail.source.remote.model

import com.fasterxml.jackson.annotation.JsonProperty

data class RemoteCompany(
    @JsonProperty("name") val name: String,
    @JsonProperty("id") val id: Int,
    @JsonProperty("logo_path") val logoPath: String?,
    @JsonProperty("origin_country") val originCountry: String
)