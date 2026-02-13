package com.dynamicwallpaper.projectivy.service

import android.app.Service
import android.app.WallpaperManager
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.IBinder
import android.util.Log
import com.bumptech.glide.Glide
import com.dynamicwallpaper.projectivy.data.ContentRepository
import com.dynamicwallpaper.projectivy.data.WallpaperDatabase
import com.dynamicwallpaper.projectivy.model.ContentType
import com.dynamicwallpaper.projectivy.model.WallpaperContent
import kotlinx.coroutines.*
import java.io.File

class DynamicWallpaperService : Service() {
    
    private val TAG = "DynamicWallpaperService"
    private lateinit var contentRepository: ContentRepository
    private lateinit var wallpaperManager: WallpaperManager
    private lateinit var database: WallpaperDatabase
    
    private val serviceScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    
    private var currentPackageName: String? = null
    private var wallpaperCache = mutableMapOf<String, List<WallpaperContent>>()
    private var currentWallpaperIndex = 0
    
    override fun onCreate() {
        super.onCreate()
        contentRepository = ContentRepository(applicationContext)
        wallpaperManager = WallpaperManager.getInstance(applicationContext)
        database = WallpaperDatabase.getDatabase(applicationContext)
        Log.d(TAG, "DynamicWallpaperService created")
    }
    
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {
            when (it.action) {
                ACTION_APP_FOCUSED -> {
                    val packageName = it.getStringExtra(EXTRA_PACKAGE_NAME)
                    packageName?.let { pkg ->
                        handleAppFocused(pkg)
                    }
                }
                ACTION_APP_UNFOCUSED -> {
                    handleAppUnfocused()
                }
                ACTION_REFRESH_CONTENT -> {
                    val packageName = it.getStringExtra(EXTRA_PACKAGE_NAME)
                    packageName?.let { pkg ->
                        refreshContentForApp(pkg)
                    }
                }
            }
        }
        return START_STICKY
    }
    
    private fun handleAppFocused(packageName: String) {
        Log.d(TAG, "App focused: $packageName")
        currentPackageName = packageName
        
        serviceScope.launch {
            try {
                // Check if we have cached content for this app
                val content = wallpaperCache[packageName] ?: run {
                    // Fetch new content
                    val newContent = contentRepository.getContentForApp(packageName)
                    wallpaperCache[packageName] = newContent
                    newContent
                }
                
                if (content.isNotEmpty()) {
                    // Pick a random wallpaper from the content
                    currentWallpaperIndex = (0 until content.size).random()
                    setWallpaper(content[currentWallpaperIndex])
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error handling app focus", e)
            }
        }
    }
    
    private fun handleAppUnfocused() {
        Log.d(TAG, "App unfocused")
        currentPackageName = null
        // Optionally restore default wallpaper
    }
    
    private fun refreshContentForApp(packageName: String) {
        serviceScope.launch {
            try {
                val content = contentRepository.getContentForApp(packageName)
                wallpaperCache[packageName] = content
                Log.d(TAG, "Refreshed content for $packageName: ${content.size} items")
            } catch (e: Exception) {
                Log.e(TAG, "Error refreshing content", e)
            }
        }
    }
    
    private suspend fun setWallpaper(content: WallpaperContent) = withContext(Dispatchers.IO) {
        try {
            Log.d(TAG, "Setting wallpaper: ${content.title}")
            
            when (content.contentType) {
                ContentType.CUSTOM_VIDEO -> {
                    // Video wallpapers would require a live wallpaper service
                    // For now, we'll extract a frame or show a placeholder
                    Log.d(TAG, "Video wallpaper not yet implemented")
                }
                else -> {
                    // Load image with Glide and set as wallpaper
                    val bitmap = Glide.with(applicationContext)
                        .asBitmap()
                        .load(content.imageUrl)
                        .submit()
                        .get()
                    
                    wallpaperManager.setBitmap(bitmap)
                    Log.d(TAG, "Wallpaper set successfully")
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error setting wallpaper", e)
        }
    }
    
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
    
    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
        Log.d(TAG, "DynamicWallpaperService destroyed")
    }
    
    companion object {
        const val ACTION_APP_FOCUSED = "com.dynamicwallpaper.projectivy.APP_FOCUSED"
        const val ACTION_APP_UNFOCUSED = "com.dynamicwallpaper.projectivy.APP_UNFOCUSED"
        const val ACTION_REFRESH_CONTENT = "com.dynamicwallpaper.projectivy.REFRESH_CONTENT"
        const val EXTRA_PACKAGE_NAME = "package_name"
    }
}
