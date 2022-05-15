package com.ebenezer.gana.movies.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.ebenezer.gana.movies.data.Movie

@Dao
interface MovieDetailDao {

    @Query("SELECT * FROM movie WHERE id =:id")
    fun getMovie(id: Long): LiveData<Movie>

}