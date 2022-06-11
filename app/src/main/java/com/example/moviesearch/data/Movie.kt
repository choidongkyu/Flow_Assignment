package com.example.moviesearch.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Movie(
    @SerializedName("title")
    val title: String,
    @SerializedName("link")
    val link: String,
    @SerializedName("pubDate")
    val pubDate: Int,
    @SerializedName("userRating")
    val userRating: String,
    @SerializedName("image")
    val image: String
)
