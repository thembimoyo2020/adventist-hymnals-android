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
import app.tinashe.hymnal.data.model.constants.DbCollections
import app.tinashe.hymnal.ui.base.ScopedViewModel
import com.google.firebase.firestore.FirebaseFirestore
import timber.log.Timber
import javax.inject.Inject

class LibraryViewModel @Inject constructor(private val fireStore: FirebaseFirestore) : ScopedViewModel() {

    private val hymnalCollections = MutableLiveData<List<HymnalCollection>>()
    val hymnalCollectionsLiveData: LiveData<List<HymnalCollection>> get() = hymnalCollections

    fun subscribe() {
        fireStore.collection(DbCollections.CATEGORIES.value)
                .addSnapshotListener { snapshot, exception ->

                    if (exception != null) {
                        Timber.e(exception)
                        return@addSnapshotListener
                    }

                    val collections = snapshot?.map { snap ->
                        snap.toObject(HymnalCollection::class.java).apply {
                            id = snap.id
                        }
                    }

                    hymnalCollections.postValue(collections?.sorted())
                }
    }

    fun loadCollection(collection: HymnalCollection, callback: (Hymnal) -> Unit) {
        collection.hymnals.forEach { id ->
            fireStore.collection(DbCollections.HYMNALS.value)
                    .document(id)
                    .addSnapshotListener { snapshot, exception ->
                        if (exception != null) {
                            Timber.e(exception)
                            return@addSnapshotListener
                        }

                        val hymnal = snapshot?.toObject(Hymnal::class.java)
                                ?: return@addSnapshotListener
                        callback.invoke(hymnal)
                    }
        }
    }
}