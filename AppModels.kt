package com.dynamicwallpaper.projectivy.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "app_configs")
data class AppConfig(
    @PrimaryKey
    val packageName: String,
    val appName: String,
    val contentType: ContentType,
    val customImagePath: String? = null,
    val customVideoPath: String? = null,
    val enabled: Boolean = true
)

enum class ContentType {
    NETFLIX,      // Uses TMDB API
    YOUTUBE,      // Uses YouTube API
    SMARTTUBE,    // Uses YouTube API
    STREMIO,      // Uses Stremio addon API
    CUSTOM_IMAGE, // User-defined image
    CUSTOM_VIDEO, // User-defined MP4 video
    DEFAULT       // System default wallpaper
}

@Entity(tableName = "cached_images")
data class CachedImage(
    @PrimaryKey
    val url: String,
    val localPath: String,
    val packageName: String,
    val timestamp: Long,
    val contentType: ContentType
)

data class WallpaperContent(
    val imageUrl: String,
    val title: String,
    val description: String,
    val contentType: ContentType
)

data class AppMapping(
    val packageName: String,
    val appName: String,
    val defaultContentType: ContentType
)

object KnownApps {
    val MAPPINGS = listOf(
        AppMapping("com.netflix.ninja", "Netflix", ContentType.NETFLIX),
        AppMapping("com.google.android.youtube.tv", "YouTube", ContentType.YOUTUBE),
        AppMapping("com.google.android.youtube.tvkids", "YouTube Kids", ContentType.YOUTUBE),
        AppMapping("com.liskovsoft.smarttubetv", "SmartTube", ContentType.SMARTTUBE),
        AppMapping("com.liskovsoft.smarttubetv.beta", "SmartTube Beta", ContentType.SMARTTUBE),
        AppMapping("com.stremio.one", "Stremio", ContentType.STREMIO),
        AppMapping("com.stremio.two", "Stremio", ContentType.STREMIO)
    )
    
    fun getContentType(packageName: String): ContentType {
        return MAPPINGS.find { it.packageName == packageName }?.defaultContentType 
            ?: ContentType.DEFAULT
    }
    
    fun getAppName(packageName: String): String {
        return MAPPINGS.find { it.packageName == packageName }?.appName 
            ?: packageName
    }
}
