package eu.jsonplaceholder.di

import dagger.Module
import dagger.Provides
import eu.jsonplaceholder.service.JsonPlaceholderService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class AppModule {
    @Singleton
    @Provides
    fun provideJsonPlaceholderService(): JsonPlaceholderService {
        return Retrofit.Builder()
            .baseUrl("http://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(JsonPlaceholderService::class.java)
    }


}
