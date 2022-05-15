package com.ebenezer.gana.movies.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.ebenezer.gana.movies.data.Movie
import com.ebenezer.gana.movies.data.local.MovieDatabase
import com.ebenezer.gana.movies.data.local.dao.MovieDetailDao

class MovieDetailRepository(context:Application) {

    private val movieDetailDao: MovieDetailDao =
        MovieDatabase.getDatabase(context).movieDetailsDao()


    fun getMovie(id:Long):LiveData<Movie> = movieDetailDao.getMovie(id)


}