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

package app.tinashe.hymnal.data.repository

import app.tinashe.hymnal.data.model.Hymnal
import app.tinashe.hymnal.data.model.HymnalCollection
import app.tinashe.hymnal.extensions.COLLECTIONS
import app.tinashe.hymnal.extensions.HYMNALS
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.tasks.await
import timber.log.Timber

class HymnalRepositoryImpl constructor(private val fireStore: FirebaseFirestore) : HymnalRepository {

    override suspend fun listCollections(): List<HymnalCollection> {
        return try {
            val snapshot = fireStore.COLLECTIONS
                    .get().await()
            snapshot.toObjects()
        } catch (ex: FirebaseFirestoreException) {
            Timber.e(ex)
            emptyList()
        }
    }

    override suspend fun listHymnals(collection: HymnalCollection): List<Hymnal> {
        return try {
            val result = arrayListOf<Hymnal>()
            coroutineScope {
                collection.hymnals.map {
                    async(Dispatchers.IO) {
                        val snapshot = fireStore.HYMNALS
                                .document(it)
                                .get().await()

                        val hymnal = snapshot.toObject<Hymnal>() ?: return@async
                        result.add(hymnal)
                    }
                }.awaitAll()
            }
            result
        } catch (ex: FirebaseFirestoreException) {
            Timber.e(ex)
            emptyList()
        }
    }
}