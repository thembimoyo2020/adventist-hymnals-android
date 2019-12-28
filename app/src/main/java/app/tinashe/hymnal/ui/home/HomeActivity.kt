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

package app.tinashe.hymnal.ui.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.lifecycle.Observer
import androidx.viewpager.widget.ViewPager
import app.tinashe.hymnal.R
import app.tinashe.hymnal.data.model.Hymn
import app.tinashe.hymnal.di.ViewModelFactory
import app.tinashe.hymnal.ui.base.BaseActivity
import app.tinashe.hymnal.ui.home.hymns.HymnFragment
import app.tinashe.hymnal.ui.home.hymns.HymnsFragmentPagerAdapter
import app.tinashe.hymnal.ui.home.hymns.search.SearchActivity
import app.tinashe.hymnal.ui.home.navigation.NavigationFragment
import app.tinashe.hymnal.utils.getViewModel
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.content_home.*
import javax.inject.Inject

class HomeActivity : BaseActivity(), HomeCallbacks {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: HomeViewModel

    private lateinit var pagerAdapter: HymnsFragmentPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        setContentView(R.layout.activity_home)

        setSupportActionBar(bottomAppBar)

        initUi()

        viewModel = getViewModel(this, viewModelFactory)
        viewModel.hymns.observe(this, Observer {
            it?.let { hymns ->
                pagerAdapter.hymns = hymns
                viewPager.adapter = pagerAdapter
            }
        })
        viewModel.number.observe(this, Observer { it?.let { number -> viewPager.currentItem = number - 1 } })
    }

    private fun initUi() {
        fab.setOnClickListener { _ ->
            val fragment = PickerFragment.newInstance {
                viewPager.currentItem = (it - 1)
            }
            fragment.show(supportFragmentManager, fragment.tag)
        }

        bottomAppBar.setNavigationOnClickListener {
            val fragment = NavigationFragment()
            fragment.show(supportFragmentManager, fragment.tag)
        }

        viewPager.pageMargin = resources.getDimensionPixelSize(R.dimen.spacing_medium)
        viewPager.setPageMarginDrawable(R.drawable.page_margin)
        viewPager.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (!fab.isShown) {
                    fab.show()
                }
            }
        })
        pagerAdapter = HymnsFragmentPagerAdapter(supportFragmentManager)
    }

    override fun onStop() {
        viewModel.savePosition(viewPager.currentItem)
        super.onStop()
    }

    override fun hymnalSelected(language: String) {
        viewModel.hymnalChanged(language)
    }

    override fun themeChanged() {
        viewPager.postDelayed({ recreate() }, 500)
    }

    override fun navigateTo(@IdRes selection: Int) {
        when (selection) {
            R.id.nav_settings -> {
            }
            R.id.nav_donate -> {
            }
            R.id.nav_feedback -> {
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == HymnFragment.RC_SEARCH && resultCode == Activity.RESULT_OK) {
            val hymn: Hymn = data?.extras?.getParcelable(SearchActivity.SELECTED_HYMN) ?: return

            viewPager.postDelayed({ viewModel.switchToHymn(hymn) }, 300)
        }
    }
}