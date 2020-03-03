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

package app.tinashe.hymnal.ui.home.library.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import app.tinashe.hymnal.data.model.Hymnal
import app.tinashe.hymnal.ui.home.library.LibraryCallbacks
import app.tinashe.hymnal.ui.home.library.adapter.vh.HymnalHolder

class LibraryListAdapter(private val callbacks: LibraryCallbacks) : ListAdapter<Hymnal, HymnalHolder>(HymnalDiff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HymnalHolder {
        return HymnalHolder.inflate(parent)
    }

    override fun onBindViewHolder(holder: HymnalHolder, position: Int) {
        val item = getItem(position) ?: return

        holder.bind(item)

        holder.itemView.setOnClickListener {
            callbacks.openHymnal(item, it)
        }
    }
}