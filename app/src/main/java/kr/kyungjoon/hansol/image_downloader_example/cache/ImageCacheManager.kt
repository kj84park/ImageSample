package kr.kyungjoon.hansol.image_downloader_example.cache

import android.content.Context
import android.graphics.Bitmap

class ImageCacheManager(context: Context) :ImageCache{

    private val fileCache = FileCache(context)
    private val memoryCache = MemoryCache.create()

    override fun addBitmap(key: String, bitmap: Bitmap?) {
        memoryCache.addBitmap(key,bitmap)
        fileCache.addBitmap(key,bitmap)
    }

    override fun getBitmap(key: String): Bitmap? {
        memoryCache.getBitmap(key)?.run {
            return this
        }
        fileCache.getBitmap(key)?.run {
            return this
        }
        return null
    }

    override fun clear() {
        memoryCache.clear()
        fileCache.clear()
    }
}