package com.example.moviesearch

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.moviesearch.RecentSearchActivity.Companion.INTENT_SEARCH_DATA
import com.example.moviesearch.adapter.MovieListAdapter
import com.example.moviesearch.databinding.ActivityMainBinding
import com.example.moviesearch.util.EventObserver
import com.example.moviesearch.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private var movieListAdapter: MovieListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        intent?.let {
            if (intent.hasExtra(INTENT_SEARCH_DATA)) {
                val search = intent.getStringExtra(INTENT_SEARCH_DATA)
                search?.let { search ->
                    binding.editText.setText(search)
                    mainViewModel.movieSearch(search)
                }
            }
        }
        movieListAdapter = MovieListAdapter(mainViewModel)
        binding.movieList.adapter = movieListAdapter
        subscribeUI()
    }

    private fun subscribeUI() {
        binding.viewModel = mainViewModel
        mainViewModel.movieDataChangedEvent.observe(this, Observer { movieList ->
            movieListAdapter?.submitList(movieList?.movies)
        })

        mainViewModel.movieDataFailEvent.observe(this, EventObserver {
            Toast.makeText(this, "데이터를 받아오는데 실패 하였습니다.", Toast.LENGTH_SHORT).show()
        })

        mainViewModel.openLinkEvent.observe(this, EventObserver { movie ->
            val browserIntent =
                Intent(Intent.ACTION_VIEW, Uri.parse(movie?.link))
            startActivity(browserIntent)
        })

        mainViewModel.recentSearchClickEvent.observe(this, EventObserver {
            startActivity(Intent(this, RecentSearchActivity::class.java))
        })
    }

    companion object {
        const val INTENT_USER_DATA = "INTENT_USER_DATA"
    }

}