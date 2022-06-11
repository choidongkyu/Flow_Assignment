package com.example.moviesearch.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RecentSearch(
    val search: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
