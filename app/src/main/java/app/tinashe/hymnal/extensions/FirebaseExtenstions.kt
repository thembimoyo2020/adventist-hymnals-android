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

package app.tinashe.hymnal.extensions

import app.tinashe.hymnal.data.model.constants.DbCollections
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

data class QueryResponse(val packet: QuerySnapshot?, val error: FirebaseFirestoreException?)

suspend fun Query.awaitRealtime() = suspendCancellableCoroutine<QueryResponse> { continuation ->
    addSnapshotListener { value, error ->
        if (error == null && continuation.isActive)
            continuation.resume(QueryResponse(value, null))
        else if (error != null && continuation.isActive)
            continuation.resume(QueryResponse(null, error))
    }
}

val FirebaseFirestore.COLLECTIONS: CollectionReference
    get() = this.collection(DbCollections.CATEGORIES.value)

val FirebaseFirestore.HYMNALS: CollectionReference
    get() = this.collection(DbCollections.HYMNALS.value)