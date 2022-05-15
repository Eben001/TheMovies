package com.ebenezer.gana.movies.ui.movieDetail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.ebenezer.gana.movies.data.Movie
import com.ebenezer.gana.movies.data.repository.MovieDetailRepository
import kotlinx.coroutines.flow.Flow

class MovieDetailViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: MovieDetailRepository =
        MovieDetailRepository(application)


    //val movie: LiveData<Movie> = repository.getMovie(id)

    fun getMovie(id: Long): Flow<Movie> = repository.getMovie(id)

}

