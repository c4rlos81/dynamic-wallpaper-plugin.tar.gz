package com.dynamicwallpaper.projectivy.data

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import android.os.ParcelFileDescriptor
import java.io.File
import java.io.FileNotFoundException

class ImageCacheProvider : ContentProvider() {
    
    private lateinit var cacheDir: File
    
    override fun onCreate(): Boolean {
        context?.let {
            cacheDir = File(it.cacheDir, "wallpaper_images")
            if (!cacheDir.exists()) {
                cacheDir.mkdirs()
            }
        }
        return true
    }
    
    override fun openFile(uri: Uri, mode: String): ParcelFileDescriptor? {
        val file = File(cacheDir, uri.lastPathSegment ?: "")
        if (!file.exists()) {
            throw FileNotFoundException("File not found: ${uri.path}")
        }
        
        return ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY)
    }
    
    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        return null
    }
    
    override fun getType(uri: Uri): String? {
        return "image/*"
    }
    
    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }
    
    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        return 0
    }
    
    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        return 0
    }
}
