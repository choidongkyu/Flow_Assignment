package com.example.moviesearch.data

import com.google.gson.annotations.SerializedName

data class MovieList(
    @SerializedName("items")
    val movies: List<Movie>
)
