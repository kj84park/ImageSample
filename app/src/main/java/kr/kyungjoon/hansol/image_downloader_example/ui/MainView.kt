package kr.kyungjoon.hansol.image_downloader_example.ui

import android.content.Context
import android.widget.ImageView

interface MainView {
    fun getContext() : Context
    fun getImage(position : Int , image: ImageView?)
}