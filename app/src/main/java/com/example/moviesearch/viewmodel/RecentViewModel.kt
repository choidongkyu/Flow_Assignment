package com.example.moviesearch.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviesearch.repository.MovieRepository
import com.example.moviesearch.util.Event

class RecentViewModel @ViewModelInject constructor(
    private val movieRepository: MovieRepository
) : ViewModel(), SearchItemClickDelegate {
    val searchDataChangedEvent = movieRepository.getRecentSearchAll()
    private val _itemClickEvent = MutableLiveData<Event<String>>()
    val itemClickEvent: LiveData<Event<String>>
        get() = _itemClickEvent

    override fun itemClicked(search: String) {
        _itemClickEvent.value = Event(search)
    }
}

interface SearchItemClickDelegate {
    fun itemClicked(search: String)
}