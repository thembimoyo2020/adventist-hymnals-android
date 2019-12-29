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

import android.os.Bundle
import android.view.View
import androidx.core.view.updatePadding
import app.tinashe.hymnal.R
import app.tinashe.hymnal.di.ViewModelFactory
import app.tinashe.hymnal.extensions.doOnApplyWindowInsets
import app.tinashe.hymnal.extensions.getViewModel
import app.tinashe.hymnal.extensions.observeNonNull
import app.tinashe.hymnal.ui.base.BaseActivity
import app.tinashe.hymnal.ui.home.hymnal.HymnalFragment
import app.tinashe.hymnal.ui.home.library.LibraryFragment
import app.tinashe.hymnal.ui.home.profile.ProfileFragment
import app.tinashe.hymnal.ui.home.search.SearchFragment
import com.pandora.bottomnavigator.BottomNavigator
import com.pandora.bottomnavigator.FragmentInfo
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_home.*
import javax.inject.Inject

class HomeActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var bottomNavigator: BottomNavigator

    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        setContentView(R.layout.activity_home)

        initUi()

        viewModel = getViewModel(this, viewModelFactory)
        viewModel.currentFragmentLiveData.observeNonNull(this) {
            it.scrollToTop()
        }
        viewModel.subscribeToFragmentCommands(bottomNavigator.resetRootFragmentCommand())
    }

    private fun initUi() {
        coordinator.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        bottomNav.doOnApplyWindowInsets { view, insets, padding ->
            view.updatePadding(bottom = padding.bottom + insets.systemWindowInsetBottom)
        }

        bottomNavigator = BottomNavigator.onCreateWithDetachability(
                fragmentContainer = R.id.fragment_container,
                bottomNavigationView = bottomNav,
                rootFragmentsFactory = mapOf(
                        R.id.nav_library to { FragmentInfo(LibraryFragment(), false) },
                        R.id.nav_read to { FragmentInfo(HymnalFragment(), true) },
                        R.id.nav_search to { FragmentInfo(SearchFragment(), false) },
                        R.id.nav_profile to { FragmentInfo(ProfileFragment(), true) }
                ), defaultTab = R.id.nav_library,
                activity = this)
    }

}