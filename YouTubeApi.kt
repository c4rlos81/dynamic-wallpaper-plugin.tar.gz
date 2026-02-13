package com.dynamicwallpaper.projectivy.api

import com.dynamicwallpaper.projectivy.model.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface YouTubeApi {
    
    @GET("subscriptions")
    suspend fun getSubscriptions(
        @Query("part") part: String = "snippet,contentDetails",
        @Query("mine") mine: Boolean = true,
        @Query("maxResults") maxResults: Int = 50,
        @Query("key") apiKey: String
    ): Response<YouTubeSubscriptionResponse>
    
    @GET("activities")
    suspend fun getActivities(
        @Query("part") part: String = "snippet,contentDetails",
        @Query("home") home: Boolean = true,
        @Query("maxResults") maxResults: Int = 20,
        @Query("key") apiKey: String
    ): Response<YouTubeActivityResponse>
    
    @GET("search")
    suspend fun searchVideos(
        @Query("part") part: String = "snippet",
        @Query("type") type: String = "video",
        @Query("order") order: String = "viewCount",
        @Query("maxResults") maxResults: Int = 20,
        @Query("relevanceLanguage") language: String = "en",
        @Query("key") apiKey: String,
        @Query("q") query: String = "trending"
    ): Response<YouTubeSearchResponse>
    
    @GET("videos")
    suspend fun getVideoDetails(
        @Query("part") part: String = "snippet,contentDetails",
        @Query("id") videoIds: String,
        @Query("key") apiKey: String
    ): Response<YouTubeVideoResponse>

    companion object {
        const val BASE_URL = "https://www.googleapis.com/youtube/v3/"
    }
}
