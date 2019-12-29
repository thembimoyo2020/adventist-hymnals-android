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

package app.tinashe.hymnal.ui.home.library

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.tinashe.hymnal.R
import app.tinashe.hymnal.di.ViewModelFactory
import app.tinashe.hymnal.extensions.getViewModel
import app.tinashe.hymnal.extensions.observeNonNull
import app.tinashe.hymnal.ui.base.BasePageFragment
import app.tinashe.hymnal.ui.home.library.adapter.CollectionsListAdapter
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_library.*
import javax.inject.Inject

class LibraryFragment : BasePageFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var listAdapter: CollectionsListAdapter

    private lateinit var viewModel: LibraryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidSupportInjection.inject(this)
        viewModel = getViewModel(this, viewModelFactory)
        viewModel.hymnalCollectionsLiveData.observeNonNull(this) {
            listAdapter.submitList(it)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_library, container, false)
    }

    override fun scrollToTop() {
        recyclerView.smoothScrollToPosition(0)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listAdapter = CollectionsListAdapter(viewModel)
        recyclerView.adapter = listAdapter

        viewModel.subscribe()
    }
}