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

package app.tinashe.hymnal.ui.home.hymns

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import app.tinashe.hymnal.R
import app.tinashe.hymnal.data.model.Hymn
import app.tinashe.hymnal.utils.inflateView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.hymn_item.*

class HymnsListAdapter constructor(private val callback: (Hymn) -> Unit) : RecyclerView.Adapter<HymnsListAdapter.HymnHolder>() {

    var hymns: ArrayList<Hymn> = arrayListOf()
        set(value) {
            val callback = HymnsDiffCallBack(field, value)
            val diffResult = DiffUtil.calculateDiff(callback)

            field.clear()
            field.addAll(value)

            diffResult.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): HymnHolder = HymnHolder.inflate(parent)

    override fun getItemCount(): Int = hymns.size

    override fun onBindViewHolder(holder: HymnHolder, position: Int) {
        val hymn = hymns[position]

        holder.bind(hymn)

        holder.itemView.setOnClickListener {
            callback.invoke(hymn)
        }
    }

    class HymnsDiffCallBack(private val oldList: List<Hymn>,
                            private val newList: List<Hymn>) : DiffUtil.Callback() {

        override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean {
            val oldItem = oldList[oldPosition]
            val newItem = newList[newPosition]

            return oldItem.id == newItem.id
        }

        override fun areItemsTheSame(oldPosition: Int, newPosition: Int): Boolean {
            val oldItem = oldList[oldPosition]
            val newItem = newList[newPosition]

            return oldItem.id == newItem.id
        }

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size
    }

    class HymnHolder constructor(override val containerView: View) :
            RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(hymn: Hymn) {
            name.text = hymn.name
        }

        companion object {
            fun inflate(parent: ViewGroup):
                    HymnHolder = HymnHolder(inflateView(R.layout.hymn_item, parent, false))
        }

    }
}