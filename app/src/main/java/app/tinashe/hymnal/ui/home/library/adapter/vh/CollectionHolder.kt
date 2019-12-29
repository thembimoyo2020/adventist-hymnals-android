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

package app.tinashe.hymnal.ui.home.library.adapter.vh

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.tinashe.hymnal.R
import app.tinashe.hymnal.data.model.Hymnal
import app.tinashe.hymnal.data.model.HymnalCollection
import app.tinashe.hymnal.extensions.horizontal
import app.tinashe.hymnal.extensions.inflateView
import app.tinashe.hymnal.ui.home.library.LibraryCallbacks
import app.tinashe.hymnal.ui.home.library.LibraryViewModel
import app.tinashe.hymnal.ui.home.library.adapter.LibraryListAdapter
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.hymnal_collection.*

class CollectionHolder constructor(override val containerView: View,
                                   private val viewModel: LibraryViewModel,
                                   private val callbacks: LibraryCallbacks) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {

    init {
        recyclerView.apply {
            horizontal()
        }
    }

    fun bind(collection: HymnalCollection) {
        tvTitle.text = collection.name

        val listAdapter = LibraryListAdapter(callbacks)
        recyclerView.adapter = listAdapter

        val hymnals = arrayListOf<Hymnal>()
        viewModel.loadCollection(collection) {
            hymnals.add(it)
            listAdapter.submitList(hymnals.distinct().sorted())
            recyclerView.post {
                recyclerView.layoutManager?.scrollToPosition(0)
            }
        }
    }

    companion object {

        fun inflate(parent: ViewGroup, viewModel: LibraryViewModel, callbacks: LibraryCallbacks): CollectionHolder = CollectionHolder(
                inflateView(R.layout.hymnal_collection, parent, false), viewModel, callbacks)
    }
}