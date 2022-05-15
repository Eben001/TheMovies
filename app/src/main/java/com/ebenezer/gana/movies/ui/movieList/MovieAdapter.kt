package com.ebenezer.gana.movies.ui.movieList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ebenezer.gana.movies.R
import com.ebenezer.gana.movies.data.Movie
import com.ebenezer.gana.movies.data.network.TmdbService
import com.ebenezer.gana.movies.databinding.ListItemMovieBinding

class MovieAdapter(private val listener: (Movie) -> Unit) :
    ListAdapter<Movie, MovieAdapter.ViewHolder>(DiffCallback) {

    inner class ViewHolder(private var binding: ListItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie) {
            binding.apply {
                movieTitle.text = movie.title
                moviePoster.load(TmdbService.POSTER_BASE_URL + movie.posterPath) {
                    placeholder(R.drawable.loading_animation)
                    error(R.drawable.ic_broken_image)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ListItemMovieBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener {
            listener(current)
        }
        holder.bind(current)
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id && oldItem.releaseDate == newItem.releaseDate
            }

        }
    }
}