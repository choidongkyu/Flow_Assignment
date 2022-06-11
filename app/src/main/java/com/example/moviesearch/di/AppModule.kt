package com.example.moviesearch.di

import android.content.Context
import com.example.moviesearch.data.AppDatabase
import com.example.moviesearch.networking.MovieApi
import com.example.moviesearch.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Singleton
    @Provides
    @MovieRetrofit
    fun provideMovieRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://openapi.naver.com/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideMovieApi(@MovieRetrofit retrofit: Retrofit): MovieApi {
        return retrofit.create(MovieApi::class.java)
    }

    @Singleton
    @Provides
    fun provideMovieRepository(
        movieApi: MovieApi,
        database: AppDatabase
    ): MovieRepository {
        return MovieRepository(movieApi, database)
    }
}