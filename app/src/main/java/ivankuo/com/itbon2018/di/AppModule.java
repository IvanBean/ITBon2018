package ivankuo.com.itbon2018.di;

import android.arch.persistence.room.Room;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ivankuo.com.itbon2018.GithubApp;
import ivankuo.com.itbon2018.api.GithubService;
import ivankuo.com.itbon2018.data.db.GithubDb;
import ivankuo.com.itbon2018.data.db.RepoDao;
import ivankuo.com.itbon2018.util.LiveDataCallAdapterFactory;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static ivankuo.com.itbon2018.data.db.GithubDb.MIGRATION_1_2;

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

    @Provides
    @Singleton
    GithubDb provideDb(GithubApp app) {
        return Room.databaseBuilder(app, GithubDb.class,"github.db")
                .addMigrations(MIGRATION_1_2)
                .build();
    }

    @Provides
    @Singleton
    RepoDao provideRepoDao(GithubDb db) {
        return db.repoDao();
    }
}