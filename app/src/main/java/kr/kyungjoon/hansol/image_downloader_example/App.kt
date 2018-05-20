package kr.kyungjoon.hansol.image_downloader_example

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import kr.kyungjoon.hansol.image_downloader_example.di.DaggerAppComponent

class App: DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
      return DaggerAppComponent.builder().application(this).build()
    }
}