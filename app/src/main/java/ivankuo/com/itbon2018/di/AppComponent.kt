package ivankuo.com.itbon2018.di

import javax.inject.Singleton

import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import ivankuo.com.itbon2018.GithubApp

@Singleton
@Component(modules = arrayOf(
        AndroidSupportInjectionModule::class,
        AppModule::class,
        ActivityBuildersModule::class))
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: GithubApp): Builder

        fun build(): AppComponent
    }

    fun inject(app: GithubApp)
}