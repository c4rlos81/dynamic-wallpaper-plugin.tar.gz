package com.dynamicwallpaper.projectivy.model

import com.google.gson.annotations.SerializedName

data class YouTubeSubscriptionResponse(
    val items: List<YouTubeSubscription>,
    val pageInfo: PageInfo
)

data class YouTubeActivityResponse(
    val items: List<YouTubeActivity>,
    val pageInfo: PageInfo
)

data class YouTubeSearchResponse(
    val items: List<YouTubeSearchResult>,
    val pageInfo: PageInfo
)

data class YouTubeVideoResponse(
    val items: List<YouTubeVideo>,
    val pageInfo: PageInfo
)

data class YouTubeSubscription(
    val id: String,
    val snippet: SubscriptionSnippet
)

data class SubscriptionSnippet(
    val title: String,
    val description: String,
    val thumbnails: Thumbnails,
    @SerializedName("resourceId") val resourceId: ResourceId
)

data class YouTubeActivity(
    val id: String,
    val snippet: ActivitySnippet,
    val contentDetails: ContentDetails?
)

data class ActivitySnippet(
    val title: String,
    val description: String,
    val thumbnails: Thumbnails,
    @SerializedName("channelTitle") val channelTitle: String
)

data class YouTubeSearchResult(
    val id: VideoId,
    val snippet: VideoSnippet
)

data class YouTubeVideo(
    val id: String,
    val snippet: VideoSnippet,
    val contentDetails: ContentDetails?
)

data class VideoId(
    val videoId: String
)

data class VideoSnippet(
    val title: String,
    val description: String,
    val thumbnails: Thumbnails,
    @SerializedName("channelTitle") val channelTitle: String
)

data class Thumbnails(
    val default: Thumbnail?,
    val medium: Thumbnail?,
    val high: Thumbnail?,
    val standard: Thumbnail?,
    val maxres: Thumbnail?
)

data class Thumbnail(
    val url: String,
    val width: Int,
    val height: Int
)

data class ResourceId(
    val channelId: String
)

data class ContentDetails(
    val upload: Upload?
)

data class Upload(
    val videoId: String
)

data class PageInfo(
    val totalResults: Int,
    val resultsPerPage: Int
)
