package com.example.moviesearch

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.moviesearch.adapter.RecentSearchListAdapter
import com.example.moviesearch.databinding.ActivityRecentSearchBinding
import com.example.moviesearch.util.EventObserver
import com.example.moviesearch.viewmodel.RecentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecentSearchActivity : AppCompatActivity() {
    private val recentViewModel: RecentViewModel by viewModels()
    private lateinit var binding: ActivityRecentSearchBinding
    private var recentSearchListAdapter: RecentSearchListAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecentSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        recentSearchListAdapter = RecentSearchListAdapter(recentViewModel)
        binding.recentSearchList.adapter = recentSearchListAdapter
        subscribeUI()
    }

    private fun subscribeUI() {
        recentViewModel.searchDataChangedEvent.observe(this, Observer {
            recentSearchListAdapter?.submitList(it)
        })

        recentViewModel.itemClickEvent.observe(this, EventObserver {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            intent.putExtra(INTENT_SEARCH_DATA, it)
            startActivity(Intent(intent))
        })
    }

    companion object {
        const val INTENT_SEARCH_DATA = "INTENT_SEARCH_DATA"
    }
}