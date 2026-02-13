package com.dynamicwallpaper.projectivy.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.leanback.app.GuidedStepSupportFragment
import androidx.leanback.widget.GuidanceStylist
import androidx.leanback.widget.GuidedAction
import com.dynamicwallpaper.projectivy.R
import com.dynamicwallpaper.projectivy.data.ContentRepository
import com.dynamicwallpaper.projectivy.data.WallpaperDatabase
import com.dynamicwallpaper.projectivy.model.AppConfig
import com.dynamicwallpaper.projectivy.model.ContentType
import com.dynamicwallpaper.projectivy.model.KnownApps
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SettingsActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        if (savedInstanceState == null) {
            GuidedStepSupportFragment.addAsRoot(this, MainSettingsFragment(), android.R.id.content)
        }
    }
    
    class MainSettingsFragment : GuidedStepSupportFragment() {
        
        private val ACTION_API_KEYS = 1L
        private val ACTION_APP_CONFIG = 2L
        private val ACTION_REFRESH_CACHE = 3L
        private val ACTION_ABOUT = 4L
        
        override fun onCreateGuidance(savedInstanceState: Bundle?): GuidanceStylist.Guidance {
            return GuidanceStylist.Guidance(
                "Dynamic Wallpaper Settings",
                "Configure your wallpaper preferences",
                "",
                null
            )
        }
        
        override fun onCreateActions(actions: MutableList<GuidedAction>, savedInstanceState: Bundle?) {
            addAction(actions, ACTION_API_KEYS, "API Keys", "Configure TMDB and YouTube API keys")
            addAction(actions, ACTION_APP_CONFIG, "App Configuration", "Configure wallpapers for each app")
            addAction(actions, ACTION_REFRESH_CACHE, "Refresh Content", "Clear cache and refresh all content")
            addAction(actions, ACTION_ABOUT, "About", "Plugin version and information")
        }
        
        override fun onGuidedActionClicked(action: GuidedAction) {
            when (action.id) {
                ACTION_API_KEYS -> {
                    add(fragmentManager, ApiKeysFragment())
                }
                ACTION_APP_CONFIG -> {
                    add(fragmentManager, AppConfigFragment())
                }
                ACTION_REFRESH_CACHE -> {
                    refreshCache()
                }
                ACTION_ABOUT -> {
                    showAbout()
                }
            }
        }
        
        private fun addAction(actions: MutableList<GuidedAction>, id: Long, title: String, desc: String) {
            actions.add(
                GuidedAction.Builder(context)
                    .id(id)
                    .title(title)
                    .description(desc)
                    .build()
            )
        }
        
        private fun refreshCache() {
            Toast.makeText(context, "Refreshing content cache...", Toast.LENGTH_SHORT).show()
            // Trigger cache refresh
            CoroutineScope(Dispatchers.Main).launch {
                try {
                    val database = context?.let { WallpaperDatabase.getDatabase(it) }
                    database?.cachedImageDao()?.deleteOldImages(0) // Clear all cache
                    Toast.makeText(context, "Cache cleared successfully", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    Toast.makeText(context, "Error clearing cache", Toast.LENGTH_SHORT).show()
                }
            }
        }
        
        private fun showAbout() {
            Toast.makeText(
                context,
                "Dynamic Wallpaper v1.0\nA Projectivy Launcher plugin",
                Toast.LENGTH_LONG
            ).show()
        }
    }
    
    class ApiKeysFragment : GuidedStepSupportFragment() {
        
        private val ACTION_TMDB_KEY = 1L
        private val ACTION_YOUTUBE_KEY = 2L
        private val ACTION_BACK = 3L
        
        override fun onCreateGuidance(savedInstanceState: Bundle?): GuidanceStylist.Guidance {
            return GuidanceStylist.Guidance(
                "API Keys",
                "Enter your API keys for content services",
                "",
                null
            )
        }
        
        override fun onCreateActions(actions: MutableList<GuidedAction>, savedInstanceState: Bundle?) {
            val repository = context?.let { ContentRepository(it) }
            
            actions.add(
                GuidedAction.Builder(context)
                    .id(ACTION_TMDB_KEY)
                    .title("TMDB API Key")
                    .description("Required for Netflix content")
                    .editable(true)
                    .build()
            )
            
            actions.add(
                GuidedAction.Builder(context)
                    .id(ACTION_YOUTUBE_KEY)
                    .title("YouTube API Key")
                    .description("Required for YouTube content")
                    .editable(true)
                    .build()
            )
            
            actions.add(
                GuidedAction.Builder(context)
                    .id(ACTION_BACK)
                    .title("Back")
                    .build()
            )
        }
        
        override fun onGuidedActionClicked(action: GuidedAction) {
            when (action.id) {
                ACTION_BACK -> {
                    fragmentManager?.popBackStack()
                }
            }
        }
        
        override fun onGuidedActionEditedAndProceed(action: GuidedAction): Long {
            val repository = context?.let { ContentRepository(it) }
            
            when (action.id) {
                ACTION_TMDB_KEY -> {
                    repository?.setTMDBApiKey(action.editDescription.toString())
                    Toast.makeText(context, "TMDB API key saved", Toast.LENGTH_SHORT).show()
                }
                ACTION_YOUTUBE_KEY -> {
                    repository?.setYouTubeApiKey(action.editDescription.toString())
                    Toast.makeText(context, "YouTube API key saved", Toast.LENGTH_SHORT).show()
                }
            }
            
            return GuidedAction.ACTION_ID_NEXT
        }
    }
    
    class AppConfigFragment : GuidedStepSupportFragment() {
        
        private var appConfigs = mutableListOf<AppConfig>()
        
        override fun onCreateGuidance(savedInstanceState: Bundle?): GuidanceStylist.Guidance {
            return GuidanceStylist.Guidance(
                "App Configuration",
                "Configure wallpaper type for each app",
                "",
                null
            )
        }
        
        override fun onCreateActions(actions: MutableList<GuidedAction>, savedInstanceState: Bundle?) {
            CoroutineScope(Dispatchers.Main).launch {
                val database = context?.let { WallpaperDatabase.getDatabase(it) }
                val configs = withContext(Dispatchers.IO) {
                    database?.appConfigDao()?.getAllConfigs() ?: emptyList()
                }
                
                // Add known apps
                KnownApps.MAPPINGS.forEach { mapping ->
                    val config = configs.find { it.packageName == mapping.packageName }
                        ?: AppConfig(
                            packageName = mapping.packageName,
                            appName = mapping.appName,
                            contentType = mapping.defaultContentType
                        )
                    
                    actions.add(
                        GuidedAction.Builder(context)
                            .id(mapping.packageName.hashCode().toLong())
                            .title(config.appName)
                            .description("Type: ${config.contentType}")
                            .build()
                    )
                }
                
                actions.add(
                    GuidedAction.Builder(context)
                        .id(999L)
                        .title("Back")
                        .build()
                )
                
                notifyActionChanged(0)
            }
        }
        
        override fun onGuidedActionClicked(action: GuidedAction) {
            if (action.id == 999L) {
                fragmentManager?.popBackStack()
            } else {
                // Edit app configuration
                Toast.makeText(context, "Edit ${action.title}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
