package kr.kyungjoon.hansol.image_downloader_example.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_detailed.*
import kr.kyungjoon.hansol.image_downloader_example.R
import kr.kyungjoon.hansol.image_downloader_example.cache.ImageCacheManager

class DetailedActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed)
        image_view?.setImageBitmap( ImageCacheManager(this)
                .getBitmap( intent.getStringExtra("imageLink")))
    }
}
