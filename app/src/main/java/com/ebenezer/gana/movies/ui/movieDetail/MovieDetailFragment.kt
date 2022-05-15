package com.ebenezer.gana.movies.ui.movieDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import coil.load
import com.ebenezer.gana.movies.R
import com.ebenezer.gana.movies.data.Movie
import com.ebenezer.gana.movies.data.network.TmdbService
import com.ebenezer.gana.movies.databinding.MovieDetailFragmentBinding
import com.ebenezer.gana.movies.readableFormat

class MovieDetailFragment : Fragment() {


    private var _binding: MovieDetailFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MovieDetailViewModel by viewModels()

    private val navigationArgs: MovieDetailFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MovieDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = navigationArgs.id

        viewModel.getMovie(id).observe(viewLifecycleOwner) {
            setData(it)
        }
    }

    private fun setData(movie: Movie) {
        binding.apply {

            moviePoster.load(TmdbService.POSTER_BASE_URL + movie.posterPath) {
                placeholder(R.drawable.loading_animation)
                error(R.drawable.ic_broken_image)
                crossfade(true)

            }
            movieBackdrop.load(TmdbService.BACKDROP_BASE_URL + movie.backdropPath) {
                placeholder(R.drawable.loading_animation)
                error(R.drawable.ic_broken_image)
                crossfade(true)

            }
            movieTitle.text = movie.title
            movieOverview.text = movie.overview
            movieReleaseDate.text = movie.releaseDate.readableFormat()
        }
    }

}