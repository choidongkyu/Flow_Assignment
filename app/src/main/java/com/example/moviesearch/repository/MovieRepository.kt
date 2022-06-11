package com.example.moviesearch.repository

import androidx.lifecycle.LiveData
import com.example.moviesearch.data.*
import com.example.moviesearch.networking.ApiResult
import com.example.moviesearch.networking.MovieApi
import com.example.moviesearch.util.Config
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val movieApi: MovieApi,
    private val database: AppDatabase
) {
    private var dataSchema: MovieList? = null

    suspend fun searchMovieData(search: String): ApiResult<MovieList> {
        val response = movieApi.searchMovieData(Config.CLIENT_ID, Config.CLIENT_SECRET, search)
        return if (response.isSuccessful && response.body() != null) {
            dataSchema = response.body()
            ApiResult.Success(dataSchema)
        } else {
            ApiResult.Error(response.code())
        }
    }

    fun getRecentSearchAll(): LiveData<List<String>> {
        return database.searchDao().getAll()
    }

    suspend fun insertRecentSearch(recentSearch: RecentSearch) {
        database.searchDao().insertSearch(recentSearch)
    }
}