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

package app.tinashe.hymnal.ui.home.hymns.search

import app.tinashe.hymnal.data.db.HymnalDatabase
import app.tinashe.hymnal.data.model.Hymn
import app.tinashe.hymnal.ui.base.ScopedViewModel
import app.tinashe.hymnal.ui.base.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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