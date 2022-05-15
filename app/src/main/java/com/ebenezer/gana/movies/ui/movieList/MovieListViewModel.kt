package com.ebenezer.gana.movies.ui.movieList

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ebenezer.gana.movies.data.Movie
import com.ebenezer.gana.movies.data.repository.MovieListRepository
import com.ebenezer.gana.movies.data.network.LoadingStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieListViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: MovieListRepository =
        MovieListRepository(application)

    val movies: Flow<List<Movie>> = repository.getMovies()

    private val _loadingStatus = MutableLiveData<LoadingStatus?>()
    val loadingStatus: MutableLiveData<LoadingStatus?> = _loadingStatus


    fun fetchFromNetwork() {
        _loadingStatus.value = LoadingStatus.loading()
        viewModelScope.launch {
            _loadingStatus.value = withContext(Dispatchers.IO) {
                repository.fetchFromNetwork()
            }
        }
    }

    fun refreshData() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllData()
        }
        fetchFromNetwork()
    }


}