package ivankuo.com.itbon2018.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ivankuo.com.itbon2018.api.GithubService;
import ivankuo.com.itbon2018.util.LiveDataCallAdapterFactory;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = ViewModelModule.class)
class AppModule {
    @Provides
    @Singleton
    GithubService provideGithubService() {
        return new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .build()
                .create(GithubService.class);
    }
}