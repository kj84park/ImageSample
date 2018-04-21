package kr.kyungjoon.hansol.image_downloader_example.ui

import android.content.Context
import io.reactivex.Observable

interface MainView {
    fun getApiService(img : String) : Observable<String>
    fun getContext() : Context
}