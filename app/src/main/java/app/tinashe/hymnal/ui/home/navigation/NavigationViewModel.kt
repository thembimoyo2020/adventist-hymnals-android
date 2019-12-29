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

package app.tinashe.hymnal.ui.home.navigation

import androidx.lifecycle.MutableLiveData
import app.tinashe.hymnal.data.db.HymnalDatabase
import app.tinashe.hymnal.data.model.Hymnal
import app.tinashe.hymnal.data.model.LocalHymnal
import app.tinashe.hymnal.data.model.constants.DbCollections
import app.tinashe.hymnal.ui.base.ScopedViewModel
import app.tinashe.hymnal.ui.base.SingleLiveEvent
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class NavigationViewModel @Inject constructor(private val fireStore: FirebaseFirestore,
                                              private val database: HymnalDatabase) : ScopedViewModel() {

    var hymnals = MutableLiveData<List<Hymnal>>()
    var updatedHymnal = SingleLiveEvent<Hymnal>()

    init {
        updateHymnalsTask()
    }

    private fun updateHymnalsTask() {

        launch {

            val localHymnals = database.hymnalDao().listAll()
            val result = arrayListOf<Hymnal>()

            localHymnals.forEach { local ->
                val hymnal = Hymnal(local.name, local.language, local.code)
                hymnal.available = true
                result.add(hymnal)
            }

            withContext(Dispatchers.Main) {
                hymnals.value = result
            }

            val collectionReference = fireStore.collection(DbCollections.HYMNALS.value)
            collectionReference.get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val snapshot = task.result

                    snapshot?.forEach { data ->
                        val hymnal = data.toObject(Hymnal::class.java)

                        if (!result.contains(hymnal)) {
                            if (hymnal.code == "eng") { //default hymnal
                                hymnal.available = true
                            }
                            result.add(hymnal)
                        }
                    }

                    result.sort()

                    hymnals.postValue(result)

                } else {
                    Timber.e(task.exception)
                }
            }
        }
    }

    fun hymnalSelected(hymnal: Hymnal) {
        if (hymnal.available) {

            //TODO: Update selected hymnal and close navigation screen

        } else {

            launch {

                val local = LocalHymnal.fromHymnal(hymnal)

                database.hymnalDao().insert(local)

                //TODO: Download file then make hymnal available local
                Thread.sleep(200)

                withContext(Dispatchers.Main) {
                    val list = ArrayList(hymnals.value ?: emptyList())
                    if (list.isEmpty()) return@withContext

                    list.find { it.code == hymnal.code }?.available = true
                    list.sortByDescending { it.available }

                    hymnals.postValue(list)

                    updatedHymnal.postValue(hymnal)
                }
            }
        }
    }
}