package kr.kyungjoon.hansol.image_downloader_example.di

import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton
import android.app.Application
import dagger.BindsInstance
import kr.kyungjoon.hansol.image_downloader_example.App


@Singleton
@Component(modules = [AndroidSupportInjectionModule::class,InjectorsModule::class])
interface AppComponent : AndroidInjector<App>{

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): AppComponent.Builder

        fun build(): AppComponent
    }
}
