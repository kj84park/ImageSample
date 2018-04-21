package kr.kyungjoon.hansol.image_downloader_example.cache

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.preference.PreferenceManager
import android.util.Base64
import java.io.ByteArrayOutputStream

class FileCache(private val context: Context) : ImageCache{

    private val pref : SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    override fun addBitmap(key: String, bitmap: Bitmap?) {

        bitmap?.let {
            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val b = baos.toByteArray()
            val value = Base64.encodeToString(b, Base64.DEFAULT)

            val editor = pref.edit()
            editor.putString(key,value)
            editor.apply()
        }
    }

    override fun getBitmap(key: String): Bitmap? {

        val encodedValue = pref.getString(key,"default")
        if(encodedValue == "default"){
            return null
        }
        val decoded = Base64.decode(encodedValue,Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decoded, 0,decoded.size)
    }

    override fun clear() {
        val editor = pref.edit()
        editor.clear()
        editor.apply()
    }

}

