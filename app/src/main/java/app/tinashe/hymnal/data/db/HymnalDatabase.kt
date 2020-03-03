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

package app.tinashe.hymnal.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import app.tinashe.hymnal.data.db.dao.HymnalDao
import app.tinashe.hymnal.data.db.dao.HymnsDao
import app.tinashe.hymnal.data.model.Hymn
import app.tinashe.hymnal.data.model.LocalHymnal

@Database(entities = [(LocalHymnal::class), (Hymn::class)], version = 1)
@TypeConverters(DataTypeConverters::class)
abstract class HymnalDatabase : RoomDatabase() {

    abstract fun hymnalDao(): HymnalDao

    abstract fun hymnsDao(): HymnsDao

    companion object {
        private const val DATABASE_NAME = "hymnal_db"

        @Volatile
        private var INSTANCE: HymnalDatabase? = null

        fun getInstance(context: Context): HymnalDatabase =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
                }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context, HymnalDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
    }
}