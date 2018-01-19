package ivankuo.com.itbon2018.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ivankuo.com.itbon2018.ui.RepoFragment

@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeRepoFragment(): RepoFragment
}