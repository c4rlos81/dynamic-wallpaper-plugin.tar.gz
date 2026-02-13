package com.dynamicwallpaper.projectivy.api

import com.dynamicwallpaper.projectivy.model.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBApi {
    
    @GET("discover/movie")
    suspend fun discoverMovies(
        @Query("api_key") apiKey: String,
        @Query("with_watch_providers") providers: String = "8", // Netflix provider ID
        @Query("watch_region") region: String = "US",
        @Query("sort_by") sortBy: String = "popularity.desc",
        @Query("page") page: Int = 1
    ): Response<TMDBMovieResponse>
    
    @GET("discover/tv")
    suspend fun discoverTV(
        @Query("api_key") apiKey: String,
        @Query("with_watch_providers") providers: String = "8",
        @Query("watch_region") region: String = "US",
        @Query("sort_by") sortBy: String = "popularity.desc",
        @Query("page") page: Int = 1
    ): Response<TMDBTVResponse>
    
    @GET("trending/{media_type}/{time_window}")
    suspend fun getTrending(
        @Path("media_type") mediaType: String = "all",
        @Path("time_window") timeWindow: String = "day",
        @Query("api_key") apiKey: String
    ): Response<TMDBTrendingResponse>
    
    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ): Response<TMDBMovie>
    
    @GET("tv/{tv_id}")
    suspend fun getTVDetails(
        @Path("tv_id") tvId: Int,
        @Query("api_key") apiKey: String
    ): Response<TMDBTVShow>

    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/"
        const val BACKDROP_SIZE_ORIGINAL = "original"
        const val BACKDROP_SIZE_W1280 = "w1280"
    }
}
