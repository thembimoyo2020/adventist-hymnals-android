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

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.tinashe.christInSong.R
import com.tinashe.christInSong.data.model.Hymnal
import com.tinashe.christInSong.utils.inflateView
import com.tinashe.christInSong.utils.random
import com.tinashe.christInSong.utils.tint
import com.tinashe.christInSong.utils.toColor
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.hymnal_item.*

class HymnalListAdapter : RecyclerView.Adapter<HymnalListAdapter.Holder>() {

    var hymnals = emptyList<Hymnal>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): Holder = Holder.inflate(parent)

    override fun getItemCount(): Int = hymnals.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(hymnals[position])
        holder.itemView.setOnClickListener { }
    }

    class Holder constructor(override val containerView: View) :
            RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(hymnal: Hymnal) {
            name.text = hymnal.name
            language.text = hymnal.language

            val color = COLORS.random()?.toColor() ?: COLORS[0].toColor()
            icon.background.tint(color)
        }

        companion object {

            /**
             * Adventist Identity colors
             *
             * https://identity.adventist.org/global-elements/color/
             */
            private val COLORS = arrayListOf("#4b207f", "#5e3929", "#7f264a", "#2f557f", "#e36520", "#448d21", "#3e8391")

            fun inflate(parent: ViewGroup):
                    Holder = Holder(inflateView(R.layout.hymnal_item, parent, false))
        }

    }
}