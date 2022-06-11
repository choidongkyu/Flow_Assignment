package com.example.moviesearch.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface RecentSearchDao {
    @Query("SELECT DISTINCT RecentSearch.search FROM RecentSearch ORDER BY RecentSearch.id DESC")
    fun getAll(): LiveData<List<String>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSearch(search: RecentSearch)
}