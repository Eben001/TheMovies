package com.ebenezer.gana.movies.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.ebenezer.gana.movies.data.Movie
import com.ebenezer.gana.movies.data.local.MovieDatabase
import com.ebenezer.gana.movies.data.local.dao.MovieListDao
import com.ebenezer.gana.movies.data.network.ErrorCode
import com.ebenezer.gana.movies.data.network.LoadingStatus
import com.ebenezer.gana.movies.data.network.TmdbService
import kotlinx.coroutines.flow.Flow
import java.net.UnknownHostException

class MovieListRepository(context: Application) {
    private val movieListDao: MovieListDao =
        MovieDatabase.getDatabase(context).movieListDao()

    private val tmdbService by lazy { TmdbService.getInstance() }


    fun getMovies(): Flow<List<Movie>> = movieListDao.getMovies()

    suspend fun fetchFromNetwork() =
        try {
            val result = tmdbService.getMovies()
            if (result.isSuccessful) {
                val movieList = result.body()
                movieList?.let {
                    movieListDao.insertMovies(it.results)
                    LoadingStatus.success()
                }
            } else {
                LoadingStatus.error(errorCode = ErrorCode.NO_DATA)
            }
        } catch (ex: UnknownHostException) {
            LoadingStatus.error(errorCode = ErrorCode.NETWORK_ERROR)
        } catch (ex: Exception) {
            LoadingStatus.error(errorCode = ErrorCode.UNKNOWN_ERROR, ex.message)
        }


    suspend fun deleteAllData() {
        movieListDao.deleteAllData()
    }


}