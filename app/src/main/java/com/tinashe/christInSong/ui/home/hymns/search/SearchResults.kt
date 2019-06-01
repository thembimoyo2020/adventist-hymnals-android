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

package com.tinashe.christInSong.ui.home.hymns.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tinashe.christInSong.R
import com.tinashe.christInSong.data.model.Hymn
import com.tinashe.christInSong.ui.home.hymns.HymnsListAdapter
import com.tinashe.christInSong.utils.vertical
import kotlinx.android.synthetic.main.fragment_list.*

class SearchResultsPagerAdapter constructor(fragmentManager: androidx.fragment.app.FragmentManager,
                                            private val results: List<Hymn>,
                                            private val callback: (Hymn) -> Unit) : androidx.fragment.app.FragmentStatePagerAdapter(fragmentManager) {

    private val languages: List<String> = results.map { it.language }.distinct()

    override fun getItem(position: Int): androidx.fragment.app.Fragment {
        return SearchResultsFragment.newInstance(results.filter { it.language == languages[position] },
                callback)
    }

    override fun getCount(): Int = languages.size

    override fun getPageTitle(position: Int): CharSequence? = languages[position]
}

class SearchResultsFragment : androidx.fragment.app.Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val results: ArrayList<Hymn> = arguments?.getParcelableArrayList(RESULTS) ?: return

        val hymnsListAdapter = HymnsListAdapter { callback?.invoke(it) }
        recyclerView.apply {
            vertical()
            adapter = hymnsListAdapter
        }

        hymnsListAdapter.hymns = results

    }

    companion object {

        private const val RESULTS = "RESULTS"
        private var callback: ((Hymn) -> Unit)? = null

        fun newInstance(results: List<Hymn>, callback: (Hymn) -> Unit): SearchResultsFragment {
            val args = Bundle().apply {
                putParcelableArrayList(RESULTS, ArrayList(results))
            }

            this.callback = callback

            return SearchResultsFragment().apply { arguments = args }
        }
    }
}