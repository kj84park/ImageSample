package kr.kyungjoon.hansol.image_downloader_example.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import kr.kyungjoon.hansol.image_downloader_example.ui.MainActivity
import kr.kyungjoon.hansol.image_downloader_example.ui.MainModule

@Module
abstract class InjectorsModule{

    @ActivityScope
    @ContributesAndroidInjector (modules = [MainModule::class])
    abstract fun mainActivity(): MainActivity

}