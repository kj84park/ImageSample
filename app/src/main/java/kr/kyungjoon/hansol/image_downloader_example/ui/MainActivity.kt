package kr.kyungjoon.hansol.image_downloader_example.ui

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Base64
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_main.*
import kr.kyungjoon.hansol.image_downloader_example.R
import kr.kyungjoon.hansol.image_downloader_example.cache.ImageCacheManager
import kr.kyungjoon.hansol.image_downloader_example.network.GettyApiService
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.*

class MainActivity : AppCompatActivity(), MainView {

    private lateinit var apiService: GettyApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initRetrofitInterface()

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

    private fun initRetrofitInterface() {
        val okHttpClient = OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val request = chain.request().newBuilder()
                            .header("Content-Type", "application/json")
                            .removeHeader("Pragma")
                            .build()
                    val response = chain.proceed(request)
                    response.cacheResponse()
                    response.newBuilder().body(ResponseBody.create(response.body()?.contentType(), Base64.encode(response.body()?.bytes(), Base64.URL_SAFE))).build()
                }.build()

        apiService = Retrofit.Builder()
                .baseUrl("http://www.gettyimagesgallery.com")
                .client(okHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build().create(GettyApiService::class.java)
    }
}
