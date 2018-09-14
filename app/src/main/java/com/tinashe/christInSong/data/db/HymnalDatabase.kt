package com.tinashe.christInSong.data.db

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import com.tinashe.christInSong.data.db.dao.HymnalDao
import com.tinashe.christInSong.data.model.LocalHymnal
import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.IO
import kotlinx.coroutines.experimental.launch
import timber.log.Timber
import kotlin.coroutines.experimental.CoroutineContext

@Database(entities = [(LocalHymnal::class)], version = 1)
@TypeConverters(DataTypeConverters::class)
abstract class HymnalDatabase : RoomDatabase() {

    abstract fun hymnalDao(): HymnalDao

    companion object {
        private const val DATABASE_NAME = "cis_hymnal_db"

        @Volatile
        private var INSTANCE: HymnalDatabase? = null

        fun getInstance(context: Context): HymnalDatabase =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
                }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context, HymnalDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .addCallback(object : Callback(), CoroutineScope {
                    override val coroutineContext: CoroutineContext
                        get() = Dispatchers.IO

                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)

                        launch {
                            getInstance(context).hymnalDao()
                                    .insert(LocalHymnal.default)

                            Timber.d("Default inserted")
                        }
                    }
                })
                .build()
    }
}