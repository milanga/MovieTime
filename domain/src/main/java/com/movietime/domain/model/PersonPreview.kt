package com.movietime.domain.model

data class PersonPreview(
    override val id: Int,
    val name: String,
    val originalName: String,
    val mediaType: String,
    val adult: Boolean,
    val popularity: Float,
    val gender: Int,
    val knownForDepartment: String,
    val profileUrl: String?,
    val knownFor: List<MediaPreview>
): GenericPreview {
    override val posterUrl: String
        get() = profileUrl ?: ""
}