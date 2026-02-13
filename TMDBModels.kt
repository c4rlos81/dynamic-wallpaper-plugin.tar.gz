package com.dynamicwallpaper.projectivy.model

import com.google.gson.annotations.SerializedName

data class TMDBMovieResponse(
    val page: Int,
    val results: List<TMDBMovie>,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
)

data class TMDBTVResponse(
    val page: Int,
    val results: List<TMDBTVShow>,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
)

data class TMDBTrendingResponse(
    val page: Int,
    val results: List<TMDBMediaItem>,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
)

data class TMDBMovie(
    val id: Int,
    val title: String,
    val overview: String,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("release_date") val releaseDate: String?,
    @SerializedName("vote_average") val voteAverage: Double,
    val popularity: Double
)

data class TMDBTVShow(
    val id: Int,
    val name: String,
    val overview: String,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("first_air_date") val firstAirDate: String?,
    @SerializedName("vote_average") val voteAverage: Double,
    val popularity: Double
)

data class TMDBMediaItem(
    val id: Int,
    val title: String? = null,
    val name: String? = null,
    val overview: String,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("media_type") val mediaType: String,
    val popularity: Double
)
