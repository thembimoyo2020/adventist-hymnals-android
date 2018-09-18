package com.tinashe.christInSong.ui.home.hymns.search

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.tinashe.christInSong.data.model.Hymn

class SearchResultsPagerAdapter constructor(fragmentManager: FragmentManager,
                                            private val results: List<Hymn>) : FragmentStatePagerAdapter(fragmentManager) {

    private val langauges: List<String> = results.map { it.language }


    override fun getItem(p0: Int): Fragment {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getCount(): Int = results.size

    override fun getPageTitle(position: Int): CharSequence? = langauges[position]
}