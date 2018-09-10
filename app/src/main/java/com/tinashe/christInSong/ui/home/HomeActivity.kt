package com.tinashe.christInSong.ui.home

import android.os.Bundle
import com.tinashe.christInSong.R
import com.tinashe.christInSong.ui.base.BaseThemedActivity
import com.tinashe.christInSong.ui.home.navigation.NavigationFragment
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : BaseThemedActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setSupportActionBar(bottomAppBar)

        fab.setOnClickListener { _ ->
            val fragment = PickerFragment()
            fragment.show(supportFragmentManager, fragment.tag)
        }

        bottomAppBar.setNavigationOnClickListener {
            val fragment = NavigationFragment()
            fragment.show(supportFragmentManager, fragment.tag)
        }
    }
}