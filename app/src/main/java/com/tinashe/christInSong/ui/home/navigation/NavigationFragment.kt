/*
 * Copyright (c) 2018. Tinashe Mzondiwa.
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

package com.tinashe.christInSong.ui.home.navigation

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.BottomSheetDialog
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.tinashe.christInSong.R
import com.tinashe.christInSong.di.ViewModelFactory
import com.tinashe.christInSong.ui.base.RoundedBottomSheetDialogFragment
import com.tinashe.christInSong.utils.getViewModel
import com.tinashe.christInSong.utils.vertical
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_navigation.*
import javax.inject.Inject

class NavigationFragment : RoundedBottomSheetDialogFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: NavigationViewModel

    private var behavior: BottomSheetBehavior<FrameLayout>? = null

    private val hymnalListAdapter = HymnalListAdapter { viewModel.hymnalSelected(it) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        dialog.setOnShowListener { dialog ->
            val bottomSheetDialog = dialog as BottomSheetDialog
            handleBottomSheetCallback(bottomSheetDialog)
        }

        return inflater.inflate(R.layout.fragment_navigation, container, false)
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

        close.setOnClickListener {
            behavior?.let { b ->
                b.state = BottomSheetBehavior.STATE_HIDDEN
            } ?: dismiss()
        }

        navView.setNavigationItemSelectedListener {

            true
        }

        val header = navView.getHeaderView(0)
        val recyclerView = header.findViewById<RecyclerView>(R.id.hymnalList)
        recyclerView.apply {
            vertical()
            adapter = hymnalListAdapter
        }
    }

    private fun handleBottomSheetCallback(dialog: BottomSheetDialog) {
        val bottomSheet = dialog.findViewById<FrameLayout>(android.support.design.R.id.design_bottom_sheet)
                ?: return

        behavior = BottomSheetBehavior.from(bottomSheet)

        behavior?.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(view: View, p1: Float) {

                header.isActivated = p1 >= 1
            }

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