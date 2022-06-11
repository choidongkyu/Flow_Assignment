package com.example.moviesearch.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesearch.R
import com.example.moviesearch.data.Movie
import com.example.moviesearch.databinding.MovieListItemBinding
import com.example.moviesearch.viewmodel.ClickDelegate

class MovieListAdapter constructor(private val delegate: ClickDelegate) :
    ListAdapter<Movie, RecyclerView.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MovieViewHolder(
            DataBindingUtil.inflate(
                inflater,
                R.layout.movie_list_item,
                parent,
                false
            ), delegate
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MovieViewHolder -> holder.apply {
                bind(getItem(position))
            }
        }
    }

    class MovieViewHolder(
        private val binding: MovieListItemBinding,
        private val delegate: ClickDelegate
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            binding.movie = movie
        }

        init {
            binding.movieContainer.setOnClickListener {
                delegate.itemClicked(binding.movie)
            }
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<Movie>() {

        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.link == newItem.link
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }
}