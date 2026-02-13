package com.dynamicwallpaper.projectivy.api

import com.dynamicwallpaper.projectivy.model.WallpaperContent
import com.dynamicwallpaper.projectivy.model.ContentType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

/**
 * Stremio API helper
 * Stremio uses addon APIs and a local streaming server
 * This class provides methods to fetch content from Stremio
 */
class StremioApiHelper {
    
    companion object {
        private const val CINEMETA_ADDON = "https://v3-cinemeta.strem.io"
        private const val DEFAULT_PORT = 11470
        
        /**
         * Fetch trending movies from Cinemeta addon
         */
        suspend fun getTrendingMovies(): List<WallpaperContent> = withContext(Dispatchers.IO) {
            try {
                val url = URL("$CINEMETA_ADDON/catalog/movie/top.json")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.connectTimeout = 5000
                connection.readTimeout = 5000
                
                val response = connection.inputStream.bufferedReader().use { it.readText() }
                connection.disconnect()
                
                parseStremioResponse(response, ContentType.STREMIO)
            } catch (e: Exception) {
                e.printStackTrace()
                emptyList()
            }
        }
        
        /**
         * Fetch trending TV shows from Cinemeta addon
         */
        suspend fun getTrendingTV(): List<WallpaperContent> = withContext(Dispatchers.IO) {
            try {
                val url = URL("$CINEMETA_ADDON/catalog/series/top.json")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.connectTimeout = 5000
                connection.readTimeout = 5000
                
                val response = connection.inputStream.bufferedReader().use { it.readText() }
                connection.disconnect()
                
                parseStremioResponse(response, ContentType.STREMIO)
            } catch (e: Exception) {
                e.printStackTrace()
                emptyList()
            }
        }
        
        /**
         * Check if Stremio streaming server is running locally
         */
        suspend fun isStremioServerRunning(): Boolean = withContext(Dispatchers.IO) {
            try {
                val url = URL("http://127.0.0.1:$DEFAULT_PORT/")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.connectTimeout = 2000
                connection.readTimeout = 2000
                
                val responseCode = connection.responseCode
                connection.disconnect()
                
                responseCode == 200
            } catch (e: Exception) {
                false
            }
        }
        
        /**
         * Get content from local Stremio library
         * Requires Stremio to be running locally
         */
        suspend fun getLocalLibrary(): List<WallpaperContent> = withContext(Dispatchers.IO) {
            try {
                if (!isStremioServerRunning()) {
                    return@withContext emptyList()
                }
                
                // This would require accessing Stremio's local API
                // The exact endpoint depends on Stremio's internal API
                // For now, we'll fall back to public addons
                getTrendingMovies() + getTrendingTV()
            } catch (e: Exception) {
                e.printStackTrace()
                emptyList()
            }
        }
        
        private fun parseStremioResponse(jsonResponse: String, contentType: ContentType): List<WallpaperContent> {
            val content = mutableListOf<WallpaperContent>()
            
            try {
                val json = JSONObject(jsonResponse)
                val metas = json.getJSONArray("metas")
                
                for (i in 0 until metas.length()) {
                    val meta = metas.getJSONObject(i)
                    
                    val poster = meta.optString("poster", "")
                    val background = meta.optString("background", "")
                    val title = meta.optString("name", meta.optString("title", ""))
                    val description = meta.optString("description", "")
                    
                    // Prefer background over poster for wallpaper
                    val imageUrl = if (background.isNotEmpty()) background else poster
                    
                    if (imageUrl.isNotEmpty()) {
                        content.add(
                            WallpaperContent(
                                imageUrl = imageUrl,
                                title = title,
                                description = description,
                                contentType = contentType
                            )
                        )
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            
            return content
        }
    }
}
