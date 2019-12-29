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

package app.tinashe.hymnal.ui.home.hymnal

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.View
import app.tinashe.hymnal.R
import app.tinashe.hymnal.data.model.Hymnal
import app.tinashe.hymnal.extensions.loadImage
import app.tinashe.hymnal.ui.base.BaseActivity
import app.tinashe.hymnal.ui.home.hymnal.categories.CategoriesPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_hymnal.*

class HymnalActivity : BaseActivity() {

    private lateinit var pagerAdapter: CategoriesPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hymnal)

        initUI()

        val hymnal = intent.getParcelableExtra<Hymnal>(ARG_HYMNAL) ?: return
        toolbarTitle.text = hymnal.name
        tvTitle.text = hymnal.name
        imgCover.loadImage(hymnal.cover)
        tvDescription.text = "Aenean consequat volutpat lorem, vel malesuada leo rutrum sit amet. Sed in finibus velit, eu rhoncus dui. Praesent sodales posuere venenatis."
    }

    private fun initUI() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        pagerAdapter = CategoriesPagerAdapter(this)
        viewPager.adapter = pagerAdapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = pagerAdapter.categories[position]
            viewPager.setCurrentItem(tab.position, true)
        }.attach()

        pagerAdapter.categories = listOf("Worship", "Early Advent", "Jesus Christ",
                "Holy Scripture", "Christian Life", "Holy Spirit")

    }

    companion object {
        private const val ARG_HYMNAL = "arg:hymnal"

        fun launch(activity: Activity, hymnal: Hymnal, sharedElement: View) {
            val intent = Intent(activity, HymnalActivity::class.java).apply {
                putExtra(ARG_HYMNAL, hymnal)
            }
            val options = ActivityOptions.makeSceneTransitionAnimation(activity,
                    sharedElement, activity.getString(R.string.transition_hymnal_cover))
            activity.startActivity(intent, options.toBundle())
        }
    }
}