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

package app.tinashe.hymnal.ui.home

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import app.tinashe.hymnal.ui.base.BasePageFragment
import app.tinashe.hymnal.ui.base.ScopedViewModel
import app.tinashe.hymnal.ui.base.SingleLiveEvent
import app.tinashe.hymnal.utils.prefs.HymnalPrefs
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val prefs: HymnalPrefs) : ScopedViewModel() {

    private val disposables = CompositeDisposable()

    private val currentFragment = SingleLiveEvent<BasePageFragment>()
    val currentFragmentLiveData: LiveData<BasePageFragment> get() = currentFragment

    fun subscribeToFragmentCommands(commands: Observable<Fragment>) {
        if (disposables.size() > 0) {
            Timber.i("Already subscribing")
            return
        }
        val disposable = commands
                .subscribe({
                    if (it is BasePageFragment) {
                        currentFragment.postValue(it)
                    }
                }, {
                    Timber.e(it)
                })

        disposables.add(disposable)
    }
}