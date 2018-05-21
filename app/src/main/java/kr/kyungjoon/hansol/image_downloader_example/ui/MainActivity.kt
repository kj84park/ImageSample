package kr.kyungjoon.hansol.image_downloader_example.ui

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.widget.ImageView
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kr.kyungjoon.hansol.image_downloader_example.R
import kr.kyungjoon.hansol.image_downloader_example.cache.ImageCacheManager
import kr.kyungjoon.hansol.image_downloader_example.network.GettyApiService
import java.util.*
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity(), MainView {

    @Inject
    lateinit var apiService: GettyApiService

    private val imageLinks :  List<String> by lazy {
        getImageLinks()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val gridAdapter = GridViewAdapter(this, R.layout.grid_item_layout,imageLinks.size)
        gridView.adapter = gridAdapter
    }

    private fun getApiService(img: String): Observable<String> {
        return apiService.getResponse(img)
    }

    override fun getContext(): Context {
        return this
    }

    override fun getImage(position : Int , image: ImageView?)
    {
        val imageLink = imageLinks[position]
        val cachedBitmap = ImageCacheManager(this).getBitmap(imageLink)
        if (cachedBitmap == null) {
            getApiService(imageLink)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            { it ->
                                it.let {
                                    val bitmap = BitmapFactory.decodeByteArray(Base64.decode(it.toByteArray(), Base64.URL_SAFE), 0, Base64.decode(it.toByteArray(), Base64.URL_SAFE).size)
                                    ImageCacheManager(this).addBitmap(imageLink, bitmap)
                                    image?.setImageBitmap(bitmap)
                                }
                            }, {})
        } else {
            image?.setImageBitmap(cachedBitmap)
        }
    }

    override fun getDetailedImage(position: Int) {
        val intent = Intent(applicationContext, DetailedActivity::class.java)
        intent.putExtra("imageLink",imageLinks[position])
        startActivity(intent)
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
