package kr.kyungjoon.hansol.image_downloader_example.cache

import android.graphics.Bitmap

interface ImageCache {
    fun addBitmap(key: String, bitmap: Bitmap?)
    fun getBitmap(key: String): Bitmap?
    fun clear()
}