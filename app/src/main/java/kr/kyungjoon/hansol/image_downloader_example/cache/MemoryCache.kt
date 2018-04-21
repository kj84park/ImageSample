package kr.kyungjoon.hansol.image_downloader_example.cache

import android.graphics.Bitmap
import android.util.LruCache

class MemoryCache : ImageCache {

    companion object {
        private val lruCache: LruCache<String, Bitmap> = LruCache<String, Bitmap>(50)
        fun create() = MemoryCache()
    }

    override fun addBitmap(key: String, bitmap: Bitmap?) {

        bitmap?.let {
            lruCache.put(key, bitmap)
        }
    }

    override fun getBitmap(key: String): Bitmap? {
        return lruCache.get(key)
    }

    override fun clear() {
        lruCache.evictAll()
    }
}