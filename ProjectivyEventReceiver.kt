package com.dynamicwallpaper.projectivy.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.dynamicwallpaper.projectivy.service.DynamicWallpaperService

/**
 * Broadcast receiver that listens for Projectivy launcher events
 * This receives app focus/unfocus events from the launcher
 */
class ProjectivyEventReceiver : BroadcastReceiver() {
    
    private val TAG = "ProjectivyEventReceiver"
    
    override fun onReceive(context: Context, intent: Intent) {
        Log.d(TAG, "Received intent: ${intent.action}")
        
        when (intent.action) {
            ACTION_APP_FOCUSED -> {
                val packageName = intent.getStringExtra(EXTRA_PACKAGE_NAME)
                Log.d(TAG, "App focused: $packageName")
                
                packageName?.let {
                    val serviceIntent = Intent(context, DynamicWallpaperService::class.java).apply {
                        action = DynamicWallpaperService.ACTION_APP_FOCUSED
                        putExtra(DynamicWallpaperService.EXTRA_PACKAGE_NAME, it)
                    }
                    context.startService(serviceIntent)
                }
            }
            
            ACTION_APP_UNFOCUSED -> {
                Log.d(TAG, "App unfocused")
                
                val serviceIntent = Intent(context, DynamicWallpaperService::class.java).apply {
                    action = DynamicWallpaperService.ACTION_APP_UNFOCUSED
                }
                context.startService(serviceIntent)
            }
            
            ACTION_APP_LAUNCHED -> {
                val packageName = intent.getStringExtra(EXTRA_PACKAGE_NAME)
                Log.d(TAG, "App launched: $packageName")
                // Optional: Handle app launch event
            }
        }
    }
    
    companion object {
        // Projectivy launcher broadcast actions
        const val ACTION_APP_FOCUSED = "com.spocky.projengmenu.APP_FOCUSED"
        const val ACTION_APP_UNFOCUSED = "com.spocky.projengmenu.APP_UNFOCUSED"
        const val ACTION_APP_LAUNCHED = "com.spocky.projengmenu.APP_LAUNCHED"
        const val EXTRA_PACKAGE_NAME = "package_name"
    }
}
