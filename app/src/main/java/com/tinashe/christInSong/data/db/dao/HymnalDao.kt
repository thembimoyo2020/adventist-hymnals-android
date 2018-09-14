package com.tinashe.christInSong.data.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.tinashe.christInSong.data.model.LocalHymnal

@Dao
interface HymnalDao : BaseDao<LocalHymnal> {

    @Query("SELECT * FROM hymnals ORDER BY lastAccessed")
    fun listAll(): List<LocalHymnal>
}