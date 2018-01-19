package ivankuo.com.itbon2018.di

import android.arch.persistence.room.Room

import javax.inject.Singleton

import dagger.Module
import dagger.Provides
import ivankuo.com.itbon2018.GithubApp
import ivankuo.com.itbon2018.api.GithubService
import ivankuo.com.itbon2018.data.db.GithubDb
import ivankuo.com.itbon2018.data.db.GithubDb.Companion.MIGRATION_1_2
import ivankuo.com.itbon2018.data.db.RepoDao
import ivankuo.com.itbon2018.util.LiveDataCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module(includes = arrayOf(ViewModelModule::class))
class AppModule {
    @Provides
    @Singleton
    fun provideGithubService(): GithubService {
        return Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .build()
                .create(GithubService::class.java)
    }

    @Provides
    @Singleton
    fun provideDb(app: GithubApp): GithubDb {
        return Room.databaseBuilder(app, GithubDb::class.java, "github.db")
                .addMigrations(MIGRATION_1_2)
                .build()
    }

    @Provides
    @Singleton
    fun provideRepoDao(db: GithubDb): RepoDao {
        return db.repoDao()
    }
}