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

package com.tinashe.christInSong.ui.splash

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.google.firebase.firestore.BuildConfig
import com.google.firebase.firestore.FirebaseFirestore
import com.tinashe.christInSong.data.model.Hymnal
import timber.log.Timber
import javax.inject.Inject

class SplashViewModel @Inject constructor(fireStore: FirebaseFirestore) : ViewModel() {

    var appVersion = MutableLiveData<String>()

    init {
        appVersion.value = "v ${BuildConfig.VERSION_NAME}"

        val hymnals = fireStore.collection("hymnals")
        hymnals.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val snapshot = task.result

                snapshot.forEach {
                    val hymnal = it.toObject(Hymnal::class.java)
                    Timber.d("$hymnal")
                }

            } else {
                Timber.e(task.exception)
            }
        }
    }
}