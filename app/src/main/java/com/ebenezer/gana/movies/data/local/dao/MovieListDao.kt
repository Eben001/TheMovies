package com.ebenezer.gana.movies.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ebenezer.gana.movies.data.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieListDao {

    // latest movies will appear at the top
    @Query("SELECT * FROM movie ORDER BY release_date DESC")
    fun getMovies(): Flow<List<Movie>>


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovies(movies: List<Movie>)

    @Query("DELETE FROM movie")
    suspend fun deleteAllData()
}