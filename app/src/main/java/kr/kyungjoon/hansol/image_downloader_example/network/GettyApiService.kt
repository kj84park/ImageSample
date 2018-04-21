package kr.kyungjoon.hansol.image_downloader_example.network

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface GettyApiService {

    var baseUrl: String
        get() = "http://www.gettyimagesgallery.com"
        set(value) = TODO()

    @GET("/Images/Thumbnails/{imagePath}")
    fun getResponse(@Path("imagePath") imagePath: String? ): Observable<String>
}