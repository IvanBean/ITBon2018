package ivankuo.com.itbon2018.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import ivankuo.com.itbon2018.MainActivity;

@Module
public abstract class BuildersModule {

    @ContributesAndroidInjector
    abstract MainActivity contributeMainActivity();

    // Add binding for other Activity/Fragment here...
}