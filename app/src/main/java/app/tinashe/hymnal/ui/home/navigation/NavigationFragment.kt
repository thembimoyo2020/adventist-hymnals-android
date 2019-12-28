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

package app.tinashe.hymnal.ui.home.navigation

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.widget.SwitchCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import app.tinashe.hymnal.R
import app.tinashe.hymnal.di.ViewModelFactory
import app.tinashe.hymnal.ui.base.RoundedBottomSheetDialogFragment
import app.tinashe.hymnal.utils.getViewModel
import app.tinashe.hymnal.utils.prefs.HymnalPrefs
import app.tinashe.hymnal.utils.vertical
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_navigation.*
import javax.inject.Inject

class NavigationFragment : RoundedBottomSheetDialogFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var prefs: HymnalPrefs

    private lateinit var viewModel: NavigationViewModel

    private lateinit var callbacks: NavigationCallbacks

    private var behavior: BottomSheetBehavior<FrameLayout>? = null

    private val hymnalListAdapter = HymnalListAdapter { viewModel.hymnalSelected(it) }

    private var themeSwitch: SwitchCompat? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        dialog?.setOnShowListener { dialog ->
            val bottomSheetDialog = dialog as BottomSheetDialog
            handleBottomSheetCallback(bottomSheetDialog)
        }

        return inflater.inflate(R.layout.fragment_navigation, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        callbacks = context as NavigationCallbacks
    }

    override fun onViewCreated(contentView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(contentView, savedInstanceState)
        AndroidSupportInjection.inject(this)

        viewModel = getViewModel(this, viewModelFactory)
        viewModel.hymnals.observe(this, Observer {

            it?.let { hymnals ->
                hymnalListAdapter.hymnals = ArrayList(hymnals)
            }
        })
        viewModel.updatedHymnal.observe(this, Observer {
            it?.let { hymnal -> hymnalListAdapter.itemChanged(hymnal) }
        })

        initUi()
    }

    private fun initUi() {
        close.setOnClickListener {
            behavior?.let { b ->
                b.state = BottomSheetBehavior.STATE_HIDDEN
            } ?: dismiss()
        }

        navView.setNavigationItemSelectedListener {
            if (it.itemId == R.id.nav_switch) {
                themeSwitch?.toggle()
            } else {
                close.performClick()
                callbacks.navigateTo(it.itemId)
            }
            true
        }

        val item = navView.menu.findItem(R.id.nav_switch)
        themeSwitch = item.actionView.findViewById(R.id.switchTheme)
        themeSwitch?.isChecked = prefs.isNightMode()
        themeSwitch?.setOnCheckedChangeListener { _, isChecked ->
            prefs.setNightMode(isChecked)
            close.performClick()
            callbacks.themeChanged()
        }

        val header = navView.getHeaderView(0)
        val recyclerView = header.findViewById<RecyclerView>(R.id.hymnalList)
        recyclerView.apply {
            vertical()
            adapter = hymnalListAdapter
        }
    }

    private fun handleBottomSheetCallback(dialog: BottomSheetDialog) {
        val bottomSheet = dialog.findViewById<FrameLayout>(R.id.design_bottom_sheet)
                ?: return

        behavior = BottomSheetBehavior.from(bottomSheet)

        behavior?.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(view: View, slide: Float) {

                header.isActivated = slide >= 1
            }

            @SuppressLint("SwitchIntDef")
            override fun onStateChanged(p0: View, state: Int) {
                when (state) {
                    BottomSheetBehavior.STATE_HIDDEN -> {
                        dismiss()
                    }
                }
            }

        })
    }
}