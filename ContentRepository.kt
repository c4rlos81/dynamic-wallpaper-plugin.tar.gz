package com.dynamicwallpaper.projectivy.data

import android.content.Context
import android.content.SharedPreferences
import com.dynamicwallpaper.projectivy.api.ApiClient
import com.dynamicwallpaper.projectivy.api.TMDBApi
import com.dynamicwallpaper.projectivy.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

class ContentRepository(private val context: Context) {
    
    private val prefs: SharedPreferences = context.getSharedPreferences("api_keys", Context.MODE_PRIVATE)
    private val database = WallpaperDatabase.getDatabase(context)
    
    private val tmdbApiKey: String
        get() = prefs.getString("tmdb_api_key", "") ?: ""
    
    private val youtubeApiKey: String
        get() = prefs.getString("youtube_api_key", "") ?: ""
    
    fun setTMDBApiKey(key: String) {
        prefs.edit().putString("tmdb_api_key", key).apply()
    }
    
    fun setYouTubeApiKey(key: String) {
        prefs.edit().putString("youtube_api_key", key).apply()
    }
    
    suspend fun getNetflixContent(): List<WallpaperContent> = withContext(Dispatchers.IO) {
        if (tmdbApiKey.isEmpty()) return@withContext emptyList()
        
        try {
            val movieResponse = ApiClient.tmdbApi.discoverMovies(
                apiKey = tmdbApiKey,
                providers = "8", // Netflix provider ID
                region = "US"
            )
            
            val tvResponse = ApiClient.tmdbApi.discoverTV(
                apiKey = tmdbApiKey,
                providers = "8",
                region = "US"
            )
            
            val content = mutableListOf<WallpaperContent>()
            
            movieResponse.body()?.results?.take(10)?.forEach { movie ->
                movie.backdropPath?.let { path ->
                    content.add(
                        WallpaperContent(
                            imageUrl = "${TMDBApi.IMAGE_BASE_URL}${TMDBApi.BACKDROP_SIZE_ORIGINAL}$path",
                            title = movie.title,
                            description = movie.overview,
                            contentType = ContentType.NETFLIX
                        )
                    )
                }
            }
            
            tvResponse.body()?.results?.take(10)?.forEach { show ->
                show.backdropPath?.let { path ->
                    content.add(
                        WallpaperContent(
                            imageUrl = "${TMDBApi.IMAGE_BASE_URL}${TMDBApi.BACKDROP_SIZE_ORIGINAL}$path",
                            title = show.name,
                            description = show.overview,
                            contentType = ContentType.NETFLIX
                        )
                    )
                }
            }
            
            content.shuffled()
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
    
    suspend fun getYouTubeContent(): List<WallpaperContent> = withContext(Dispatchers.IO) {
        if (youtubeApiKey.isEmpty()) return@withContext emptyList()
        
        try {
            val searchResponse = ApiClient.youtubeApi.searchVideos(
                apiKey = youtubeApiKey,
                query = "trending",
                maxResults = 20
            )
            
            val content = mutableListOf<WallpaperContent>()
            
            searchResponse.body()?.items?.forEach { result ->
                result.snippet.thumbnails.maxres?.url?.let { url ->
                    content.add(
                        WallpaperContent(
                            imageUrl = url,
                            title = result.snippet.title,
                            description = result.snippet.description,
                            contentType = ContentType.YOUTUBE
                        )
                    )
                } ?: result.snippet.thumbnails.high?.url?.let { url ->
                    content.add(
                        WallpaperContent(
                            imageUrl = url,
                            title = result.snippet.title,
                            description = result.snippet.description,
                            contentType = ContentType.YOUTUBE
                        )
                    )
                }
            }
            
            content
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
    
    suspend fun getStremioContent(): List<WallpaperContent> = withContext(Dispatchers.IO) {
        try {
            // Try to get content from Stremio's public Cinemeta addon
            val movies = StremioApiHelper.getTrendingMovies()
            val tvShows = StremioApiHelper.getTrendingTV()
            
            (movies + tvShows).shuffled().take(20)
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
    
    suspend fun getContentForApp(packageName: String): List<WallpaperContent> {
        val config = database.appConfigDao().getConfig(packageName)
        
        return when (config?.contentType ?: KnownApps.getContentType(packageName)) {
            ContentType.NETFLIX -> getNetflixContent()
            ContentType.YOUTUBE, ContentType.SMARTTUBE -> getYouTubeContent()
            ContentType.STREMIO -> getStremioContent()
            ContentType.CUSTOM_IMAGE -> {
                config?.customImagePath?.let {
                    listOf(
                        WallpaperContent(
                            imageUrl = "file://$it",
                            title = "Custom Wallpaper",
                            description = "",
                            contentType = ContentType.CUSTOM_IMAGE
                        )
                    )
                } ?: emptyList()
            }
            ContentType.CUSTOM_VIDEO -> {
                config?.customVideoPath?.let {
                    listOf(
                        WallpaperContent(
                            imageUrl = "file://$it",
                            title = "Custom Video Wallpaper",
                            description = "",
                            contentType = ContentType.CUSTOM_VIDEO
                        )
                    )
                } ?: emptyList()
            }
            else -> emptyList()
        }
    }
    
    suspend fun cacheImage(url: String, packageName: String, contentType: ContentType): String? {
        // Check if already cached
        database.cachedImageDao().getImageByUrl(url)?.let {
            return it.localPath
        }
        
        // Download and cache the image
        // This would be implemented using Glide or similar
        // For now, return the URL as-is
        return url
    }
}
