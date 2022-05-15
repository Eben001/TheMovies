package com.ebenezer.gana.movies.ui.movieList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.ebenezer.gana.movies.R
import com.ebenezer.gana.movies.data.network.ErrorCode
import com.ebenezer.gana.movies.data.network.Status
import com.ebenezer.gana.movies.databinding.MovieListFragmentBinding
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MovieListFragment : Fragment() {


    private var _binding: MovieListFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MovieListViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MovieListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding.movieList) {
            adapter = MovieAdapter {
                val action =
                    MovieListFragmentDirections.actionMovieListFragmentToMovieDetailFragment(it.id)
                findNavController().navigate(action)
            }

        }

        lifecycleScope.launch {
            viewModel.movies.catch { it.printStackTrace() }
                .collect { movies ->
                    (binding.movieList.adapter as MovieAdapter).submitList(movies)
                    if (movies.isEmpty()) {
                        viewModel.fetchFromNetwork()
                    }
                }
        }

        viewModel.loadingStatus.observe(viewLifecycleOwner) { loadingStatus ->
            when (loadingStatus?.status) {
                Status.LOADING -> {
                    binding.loadingStatus.visibility = View.VISIBLE
                    binding.statusError.visibility = View.INVISIBLE
                }
                Status.SUCCESS -> {
                    binding.loadingStatus.visibility = View.INVISIBLE
                    binding.statusError.visibility = View.INVISIBLE
                }
                Status.ERROR -> {
                    binding.loadingStatus.visibility = View.INVISIBLE
                    showErrorMessage(loadingStatus.errorCode, loadingStatus.message)
                    binding.statusError.visibility = View.VISIBLE
                }
                else -> {}
            }
            binding.swipeRefresh.isRefreshing = false


        }
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.refreshData()
        }


    }

    private fun showErrorMessage(errorCode: ErrorCode?, message: String?) {
        when (errorCode) {
            ErrorCode.NO_DATA -> binding.statusError.text =
                getString(R.string.error_no_data)
            ErrorCode.NETWORK_ERROR -> binding.statusError.text =
                getString(R.string.error_network)
            ErrorCode.UNKNOWN_ERROR -> binding.statusError.text =
                getString(R.string.unknown_error, message)
            else -> {}
        }
    }

}