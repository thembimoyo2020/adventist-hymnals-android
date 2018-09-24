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

package com.tinashe.christInSong.ui.home

import android.arch.lifecycle.MutableLiveData
import com.tinashe.christInSong.data.db.HymnalDatabase
import com.tinashe.christInSong.data.model.Hymn
import com.tinashe.christInSong.ui.base.ScopedViewModel
import com.tinashe.christInSong.utils.prefs.HymnalPrefs
import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.android.Main
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.withContext
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val database: HymnalDatabase,
                                        private val prefs: HymnalPrefs) : ScopedViewModel() {

    var hymns = MutableLiveData<List<Hymn>>()
    var number = MutableLiveData<Int>()

    init {
        fetchHymns()
    }

    private fun fetchHymns() {
        launch {
            val list = database.hymnsDao().getHymns(prefs.getLanguage())

            withContext(Dispatchers.Main) {
                hymns.value = list
                number.value = prefs.getLastHymnNumber()
            }
        }
    }

    fun hymnalChanged(language: String) {
        prefs.setLanguage(language)

        fetchHymns()
    }

    fun savePosition(position: Int) {
        prefs.setLastHymnNumber(position + 1)
        number.postValue(position + 1)
    }

    fun switchToHymn(hymn: Hymn){
        prefs.setLastHymnNumber(hymn.number)

        hymnalChanged(hymn.language)
    }
}