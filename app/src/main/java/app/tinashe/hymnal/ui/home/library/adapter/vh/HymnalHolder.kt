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
import androidx.core.text.parseAsHtml
import androidx.recyclerview.widget.RecyclerView
import app.tinashe.hymnal.R
import app.tinashe.hymnal.data.model.Hymnal
import app.tinashe.hymnal.extensions.inflateView
import app.tinashe.hymnal.extensions.loadImage
import app.tinashe.hymnal.extensions.toColor
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.hymnal_card.*

class HymnalHolder constructor(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(hymnal: Hymnal) {
        tvTitle.text = hymnal.name.parseAsHtml()
        chipLanguage.text = hymnal.language

        val color = COLORS.random().toColor()
        card.setCardBackgroundColor(color)

        if (hymnal.cover.isNullOrEmpty()) {
            imgCover.setImageDrawable(null)
        } else {
            imgCover.loadImage(hymnal.cover)
        }
    }

    companion object {
        private val COLORS = arrayListOf("#4b207f", "#5e3929", "#7f264a", "#2f557f", "#e36520", "#448d21", "#3e8391")
        fun inflate(parent: ViewGroup):
                HymnalHolder = HymnalHolder(inflateView(R.layout.hymnal_card, parent, false))
    }
}