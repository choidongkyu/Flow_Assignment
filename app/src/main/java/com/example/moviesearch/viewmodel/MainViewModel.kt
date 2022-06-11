package com.example.moviesearch.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesearch.data.Movie
import com.example.moviesearch.data.MovieList
import com.example.moviesearch.data.RecentSearch
import com.example.moviesearch.networking.ApiResult
import com.example.moviesearch.repository.MovieRepository
import com.example.moviesearch.util.Event
import kotlinx.coroutines.launch


class MainViewModel @ViewModelInject constructor(
    private val movieRepository: MovieRepository
) : ViewModel(), ClickDelegate {
    private val _movieDataFailEvent = MutableLiveData<Event<Int>>()
    val movieDataFailEvent: LiveData<Event<Int>>
        get() = _movieDataFailEvent

    private val _movieDataChangedEvent = MutableLiveData<MovieList?>()
    val movieDataChangedEvent: LiveData<MovieList?>
        get() = _movieDataChangedEvent

    private val _openLinkEvent = MutableLiveData<Event<Movie?>>()
    val openLinkEvent: LiveData<Event<Movie?>>
        get() = _openLinkEvent

    private val _recentSearchClickEvent = MutableLiveData<Event<Unit>>()
    val recentSearchClickEvent: LiveData<Event<Unit>>
        get() = _recentSearchClickEvent

    var searchQuery: String = ""


    fun movieSearch(searchQuery: String) {
        viewModelScope.launch {
            when (val result = movieRepository.searchMovieData(searchQuery)) {
                is ApiResult.Success -> {
                    _movieDataChangedEvent.postValue(result.data)
                }
                is ApiResult.Error -> {
                    _movieDataFailEvent.postValue(Event(result.errorCode))
                }
            }
            movieRepository.insertRecentSearch(RecentSearch(searchQuery))
        }
    }

    fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        searchQuery = s.toString()
    }

    override fun itemClicked(movie: Movie?) {
        _openLinkEvent.postValue(Event(movie))
    }

    fun recentSearchClicked() {
        _recentSearchClickEvent.value = Event(Unit)
    }
}

interface ClickDelegate {
    fun itemClicked(movie: Movie?)
}