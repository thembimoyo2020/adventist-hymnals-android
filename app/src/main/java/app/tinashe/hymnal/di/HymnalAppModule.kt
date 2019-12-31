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

package app.tinashe.hymnal.di

import android.content.Context
import app.tinashe.hymnal.HymnalApp
import app.tinashe.hymnal.data.db.HymnalDatabase
import app.tinashe.hymnal.data.repository.HymnalRepository
import app.tinashe.hymnal.data.repository.HymnalRepositoryImpl
import app.tinashe.hymnal.utils.prefs.HymnalPrefs
import app.tinashe.hymnal.utils.prefs.HymnalPrefsImpl
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [FirebaseModule::class])
internal class HymnalAppModule {

    @Provides
    @Singleton
    fun provideContext(app: HymnalApp): Context = app

    @Provides
    @Singleton
    fun provideLocalDatabase(context: Context): HymnalDatabase = HymnalDatabase.getInstance(context)

    @Provides
    @Singleton
    fun providePrefs(context: Context): HymnalPrefs = HymnalPrefsImpl(context)

    @Provides
    @Singleton
    fun provideRepository(firestore: FirebaseFirestore):
            HymnalRepository = HymnalRepositoryImpl(firestore)
}