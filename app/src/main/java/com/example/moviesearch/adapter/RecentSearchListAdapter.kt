package com.example.moviesearch.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesearch.R
import com.example.moviesearch.databinding.RecentSearchItemBinding
import com.example.moviesearch.viewmodel.RecentViewModel
import com.example.moviesearch.viewmodel.SearchItemClickDelegate

class RecentSearchListAdapter constructor(private val delegate: SearchItemClickDelegate) :
    ListAdapter<String, RecyclerView.ViewHolder>(DiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return RecentSearchViewHolder(
            DataBindingUtil.inflate(
                inflater,
                R.layout.recent_search_item,
                parent,
                false
            ), delegate
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is RecentSearchViewHolder -> holder.apply {
                bind(getItem(position))
            }
        }
    }

    class RecentSearchViewHolder(
        private val binding: RecentSearchItemBinding,
        private val delegate: SearchItemClickDelegate
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(search: String) {
            binding.recentSearch.text = search
        }

        init {
            binding.searchContainer.setOnClickListener {
                delegate.itemClicked(binding.recentSearch.text as String)
            }
        }
    }

    override fun getItemCount(): Int {
        if(super.getItemCount() > 10) {
            return 10
        }
        return super.getItemCount()
    }

    private class DiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }
}