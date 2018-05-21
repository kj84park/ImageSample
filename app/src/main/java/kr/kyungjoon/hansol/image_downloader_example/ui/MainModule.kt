package kr.kyungjoon.hansol.image_downloader_example.ui

import android.util.Base64
import dagger.Module
import dagger.Provides
import kr.kyungjoon.hansol.image_downloader_example.di.ActivityScope
import kr.kyungjoon.hansol.image_downloader_example.network.GettyApiService
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory


@Module
class MainModule{

    @Provides
    @ActivityScope
    public fun provideRetrofit() : GettyApiService {
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

        return  Retrofit.Builder()
                .baseUrl("http://www.gettyimagesgallery.com")
                .client(okHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build().create(GettyApiService::class.java)
    }
}