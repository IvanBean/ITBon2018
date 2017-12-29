package ivankuo.com.itbon2018.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import ivankuo.com.itbon2018.MainActivity;

@Module
public abstract class ActivityBuildersModule {
    @ContributesAndroidInjector(modules = FragmentBuildersModule.class)
    abstract MainActivity contributeMainActivity();
}