package eu.jsonplaceholder.di

import android.app.Application
import androidx.room.Room
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import eu.jsonplaceholder.db.CommentDao
import eu.jsonplaceholder.db.PlaceholderDb
import eu.jsonplaceholder.db.PostDao
import eu.jsonplaceholder.db.UserDao
import eu.jsonplaceholder.service.JsonPlaceholderService
import eu.jsonplaceholder.utils.DbUtils
import eu.jsonplaceholder.utils.DispatcherProvider
import eu.jsonplaceholder.utils.DispatcherProviderImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class AppModule {
    @Singleton
    @Provides
    fun provideJsonPlaceholderService(client : OkHttpClient): JsonPlaceholderService {
        return Retrofit.Builder()
            .baseUrl("http://jsonplaceholder.typicode.com/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
            .create(JsonPlaceholderService::class.java)
    }

    @Provides
    fun provideOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient {
        val b = OkHttpClient.Builder()
        b.addInterceptor(interceptor)
        return b.build()
    }

    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }



    @Singleton
    @Provides
    fun provideDatabase(applicationContext: Application): PlaceholderDb {
        return Room.databaseBuilder(applicationContext,
            PlaceholderDb::class.java,DbUtils.dbName )
            .build()
    }

    @Singleton
    @Provides
    fun provideUserDao(db: PlaceholderDb): UserDao {
        return db.userDao()
    }

    @Singleton
    @Provides
    fun providePostDao(db: PlaceholderDb): PostDao {
        return db.postDao()
    }

    @Singleton
    @Provides
    fun provideCommentDao(db: PlaceholderDb): CommentDao {
        return db.commentDao()
    }

    @Singleton
    @Provides
    fun providesDispatcherProvider(): DispatcherProvider{
        return DispatcherProviderImpl()
    }
}
