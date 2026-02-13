package com.dynamicwallpaper.projectivy.data

import android.content.Context
import androidx.room.*
import com.dynamicwallpaper.projectivy.model.AppConfig
import com.dynamicwallpaper.projectivy.model.CachedImage
import com.dynamicwallpaper.projectivy.model.ContentType

@Dao
interface AppConfigDao {
    @Query("SELECT * FROM app_configs")
    suspend fun getAllConfigs(): List<AppConfig>
    
    @Query("SELECT * FROM app_configs WHERE packageName = :packageName")
    suspend fun getConfig(packageName: String): AppConfig?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertConfig(config: AppConfig)
    
    @Update
    suspend fun updateConfig(config: AppConfig)
    
    @Delete
    suspend fun deleteConfig(config: AppConfig)
    
    @Query("DELETE FROM app_configs WHERE packageName = :packageName")
    suspend fun deleteConfigByPackage(packageName: String)
}

@Dao
interface CachedImageDao {
    @Query("SELECT * FROM cached_images WHERE packageName = :packageName")
    suspend fun getImagesForApp(packageName: String): List<CachedImage>
    
    @Query("SELECT * FROM cached_images WHERE url = :url")
    suspend fun getImageByUrl(url: String): CachedImage?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImage(image: CachedImage)
    
    @Query("DELETE FROM cached_images WHERE packageName = :packageName")
    suspend fun deleteImagesForApp(packageName: String)
    
    @Query("DELETE FROM cached_images WHERE timestamp < :cutoffTime")
    suspend fun deleteOldImages(cutoffTime: Long)
    
    @Query("SELECT * FROM cached_images ORDER BY timestamp DESC LIMIT :limit")
    suspend fun getRecentImages(limit: Int): List<CachedImage>
}

@Database(
    entities = [AppConfig::class, CachedImage::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class WallpaperDatabase : RoomDatabase() {
    abstract fun appConfigDao(): AppConfigDao
    abstract fun cachedImageDao(): CachedImageDao
    
    companion object {
        @Volatile
        private var INSTANCE: WallpaperDatabase? = null
        
        fun getDatabase(context: Context): WallpaperDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WallpaperDatabase::class.java,
                    "wallpaper_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

class Converters {
    @TypeConverter
    fun fromContentType(value: ContentType): String {
        return value.name
    }
    
    @TypeConverter
    fun toContentType(value: String): ContentType {
        return ContentType.valueOf(value)
    }
}
