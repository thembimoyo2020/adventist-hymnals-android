package com.tinashe.christInSong.data.repository

import android.arch.lifecycle.LiveData
import com.tinashe.christInSong.data.model.Hymnal

interface HymnalRepository {

    fun listHymnals(): LiveData<List<Hymnal>>
}