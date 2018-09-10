package com.tinashe.christInSong.ui.home.navigation

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.tinashe.christInSong.data.model.Hymnal
import timber.log.Timber
import javax.inject.Inject

class NavigationViewModel @Inject constructor(fireStore: FirebaseFirestore) : ViewModel() {

    var hymnals = MutableLiveData<List<Hymnal>>()

    init {
        val collectionReference = fireStore.collection("hymnals")
        collectionReference.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val snapshot = task.result

                val list = arrayListOf<Hymnal>()

                snapshot.forEach {
                    list.add(it.toObject(Hymnal::class.java))
                }

                hymnals.value = list

            } else {
                Timber.e(task.exception)
            }
        }
    }
}