package com.tinashe.christInSong.ui.home.hymns.search

import android.app.Activity
import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.SearchView
import com.tinashe.christInSong.R
import com.tinashe.christInSong.di.ViewModelFactory
import com.tinashe.christInSong.ui.base.BaseThemedActivity
import com.tinashe.christInSong.utils.getViewModel
import kotlinx.android.synthetic.main.activity_search.*
import timber.log.Timber
import javax.inject.Inject

class SearchActivity : BaseThemedActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: SearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        searchView.requestFocus()
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean = false

            override fun onQueryTextChange(query: String?): Boolean {
                viewModel.search(query)
                return true
            }
        })

        viewModel = getViewModel(this, viewModelFactory)
        viewModel.searchResults.observe(this, Observer {
            Timber.d("RESULTS [${it?.size}]")
        })
    }

    companion object {

        fun launch(activity: Activity){
            val intent = Intent(activity, SearchActivity::class.java)
            activity.startActivity(intent)
        }
    }
}