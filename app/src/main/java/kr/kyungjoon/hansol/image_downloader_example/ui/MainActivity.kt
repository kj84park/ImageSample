package kr.kyungjoon.hansol.image_downloader_example.ui

import android.content.Context
import android.os.Bundle
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_main.*
import kr.kyungjoon.hansol.image_downloader_example.R
import kr.kyungjoon.hansol.image_downloader_example.cache.ImageCacheManager
import kr.kyungjoon.hansol.image_downloader_example.network.GettyApiService
import java.util.*
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity(), MainView {

    @Inject
    lateinit var apiService: GettyApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val gridAdapter = GridViewAdapter(this, R.layout.grid_item_layout,
                ImageCacheManager(this) , getImageLinks())
        gridView.adapter = gridAdapter
    }

    override fun getApiService(img: String): Observable<String> {
        return apiService.getResponse(img)
    }

    override fun getContext(): Context {
        return this
    }

    private fun getImageLinks(): ArrayList<String> {
        val imageItems = ArrayList<String>()
        val imageLinks = getContext().resources.obtainTypedArray(R.array.image_links)
        for (i in 0 until imageLinks.length()) {
            imageItems.add(imageLinks.getString(i))
        }
        return imageItems
    }

}
