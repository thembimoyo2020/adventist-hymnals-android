/*
 * Copyright (c) 2019. Tinashe Mzondiwa.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tinashe.christInSong.ui.home.hymns.search

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import com.tinashe.christInSong.R
import com.tinashe.christInSong.data.model.Hymn
import com.tinashe.christInSong.di.ViewModelFactory
import com.tinashe.christInSong.ui.base.BaseThemedActivity
import com.tinashe.christInSong.utils.getViewModel
import com.tinashe.christInSong.utils.hide
import com.tinashe.christInSong.utils.show
import kotlinx.android.synthetic.main.activity_search.*
import javax.inject.Inject

class SearchActivity : BaseThemedActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: SearchViewModel

    private lateinit var pagerAdapter: SearchResultsPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        searchView.requestFocus()
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean = false

            override fun onQueryTextChange(query: String?): Boolean {
                viewModel.search(query)
                return true
            }
        })

        viewModel = getViewModel(this, viewModelFactory)
        viewModel.searchResults.observe(this, Observer {
            when {
                it == null || it.isEmpty() -> {
                    viewPager.hide()
                    tabLayout.hide()
                }
                else -> showResults(it)
            }
        })

        viewModel.search("")
    }

    private fun showResults(hymns: List<Hymn>) {
        pagerAdapter = SearchResultsPagerAdapter(supportFragmentManager, hymns) {
            val args = Bundle().apply { putParcelable(SELECTED_HYMN, it) }
            val data = Intent().apply { putExtras(args) }

            setResult(Activity.RESULT_OK, data)
            supportFinishAfterTransition()
        }

        viewPager.adapter = pagerAdapter

        if (hymns.map { it.language }.distinct().size > 1) {
            tabLayout.setupWithViewPager(viewPager)

            tabLayout.show()
        } else {
            tabLayout.hide()
        }
        viewPager.show()
    }

    companion object {

        const val SELECTED_HYMN = "SELECTED_HYMN"

        fun launch(activity: Activity, resultCode: Int) {
            val intent = Intent(activity, SearchActivity::class.java)
            activity.startActivityForResult(intent, resultCode)
        }
    }
}