package com.tinashe.christInSong.ui.home.navigation

import android.arch.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.tinashe.christInSong.data.db.HymnalDatabase
import com.tinashe.christInSong.data.model.Collections
import com.tinashe.christInSong.data.model.Hymnal
import com.tinashe.christInSong.data.model.LocalHymnal
import com.tinashe.christInSong.ui.base.ScopedViewModel
import com.tinashe.christInSong.ui.base.SingleLiveEvent
import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.android.Main
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.withContext
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
                hymnals.postValue(result)
            }

            val collectionReference = fireStore.collection(Collections.HYMNALS)
            collectionReference.get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val snapshot = task.result

                    snapshot.forEach { data ->
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

            //TODO: Update selected hymnal ad close navigation screen

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