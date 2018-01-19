package ivankuo.com.itbon2018.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ivankuo.com.itbon2018.MainActivity

@Module
abstract class ActivityBuildersModule {
    @ContributesAndroidInjector(modules = arrayOf(FragmentBuildersModule::class))
    abstract fun contributeMainActivity(): MainActivity
}