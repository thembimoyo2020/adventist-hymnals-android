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

package app.tinashe.hymnal.ui.home.library

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.tinashe.hymnal.data.model.Hymnal
import app.tinashe.hymnal.data.model.HymnalCollection
import app.tinashe.hymnal.data.repository.HymnalRepository
import app.tinashe.hymnal.ui.base.ScopedViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class LibraryViewModel @Inject constructor(private val repository: HymnalRepository) : ScopedViewModel() {

    private val hymnalCollections = MutableLiveData<List<HymnalCollection>>()
    val hymnalCollectionsLiveData: LiveData<List<HymnalCollection>> get() = hymnalCollections

    fun subscribe() {

        launch {
            val collections = repository.listCollections()
            hymnalCollections.postValue(collections.sorted())
        }
    }

    fun collectionHymnals(collection: HymnalCollection): LiveData<List<Hymnal>> {

        val mutableLiveData = MutableLiveData<List<Hymnal>>()

        launch {
            val hymnals = repository.listHymnals(collection)
            mutableLiveData.postValue(hymnals)
        }

        return mutableLiveData
    }
}