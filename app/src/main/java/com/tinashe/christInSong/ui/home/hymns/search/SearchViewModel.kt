package com.tinashe.christInSong.ui.home.hymns.search

import com.tinashe.christInSong.data.db.HymnalDatabase
import com.tinashe.christInSong.data.model.Hymn
import com.tinashe.christInSong.ui.base.ScopedViewModel
import com.tinashe.christInSong.ui.base.SingleLiveEvent
import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.IO
import kotlinx.coroutines.experimental.android.Main
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.withContext
import javax.inject.Inject

class SearchViewModel @Inject constructor(private val database: HymnalDatabase) : ScopedViewModel() {

    var searchResults = SingleLiveEvent<List<Hymn>>()

    fun search(query: String?) {

        if (query == null) {
            searchResults.postValue(emptyList())
            return
        }

        launch {
            withContext(Dispatchers.IO) {
                val results = database.hymnsDao().search("%$query%")

                withContext(Dispatchers.Main) {
                    searchResults.postValue(results)
                }
            }
        }
    }
}