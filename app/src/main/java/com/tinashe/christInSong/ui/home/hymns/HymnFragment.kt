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

package com.tinashe.christInSong.ui.home.hymns

import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tinashe.christInSong.R
import com.tinashe.christInSong.data.model.Hymn
import kotlinx.android.synthetic.main.fragment_hymn.*

class HymnFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_hymn, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val hymn: Hymn? = arguments?.getParcelable(HYMN)

        hymnText.text = Html.fromHtml(hymn?.body)
    }

    companion object {

        private const val HYMN = "HYMN"

        fun newInstance(hymn: Hymn): HymnFragment {

            val bundle = Bundle().apply {
                putParcelable(HYMN, hymn)
            }

            return HymnFragment().apply { arguments = bundle }
        }
    }
}