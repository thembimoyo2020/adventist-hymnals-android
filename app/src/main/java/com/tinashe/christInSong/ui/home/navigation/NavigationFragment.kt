package com.tinashe.christInSong.ui.home.navigation

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tinashe.christInSong.R
import com.tinashe.christInSong.di.ViewModelFactory
import com.tinashe.christInSong.ui.base.RoundedBottomSheetDialogFragment
import com.tinashe.christInSong.utils.getViewModel
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_navigation.*
import timber.log.Timber
import javax.inject.Inject

class NavigationFragment : RoundedBottomSheetDialogFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: NavigationViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_navigation, container, false)
    }

    override fun onViewCreated(contentView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(contentView, savedInstanceState)
        AndroidSupportInjection.inject(this)

        viewModel = getViewModel(this, viewModelFactory)
        viewModel.hymnals.observe(this, Observer {
            Timber.d("Hymnals: $it")
        })

        navView.setNavigationItemSelectedListener {

            true
        }
    }
}