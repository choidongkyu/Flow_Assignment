package com.example.moviesearch.networking

import com.example.moviesearch.data.MovieList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface MovieApi {
    @GET("v1/search/movie.json")
    suspend fun searchMovieData(
        @Header("X-Naver-Client-Id") clientId: String,
        @Header("X-Naver-Client-Secret") clientSecret: String,
        @Query("query") search: String
    ): Response<MovieList>
}