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

package com.tinashe.christInSong.data.model

import androidx.room.Entity
import java.util.*

@Entity(tableName = "hymnals")
class LocalHymnal : Hymnal() {

    var lastAccessed: Date? = null

    companion object {

        val default: LocalHymnal = LocalHymnal().apply {
            code = "eng"
            name = "Christ In Song"
            language = "English"
            available = true
            lastAccessed = Date()
        }

        fun fromHymnal(hymnal: Hymnal): LocalHymnal {

            val local = LocalHymnal()

            local.name = hymnal.name
            local.language = hymnal.language
            local.code = hymnal.code
            local.lastAccessed = Calendar.getInstance().time
            local.available = true

            return local
        }
    }
}