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

package app.tinashe.hymnal.data.model.constants

enum class UiPref(val value: String) {
    DAY("day"),
    NIGHT("night"),
    BATTERY_SAVER("battery_saver"),
    FOLLOW_SYSTEM("follow_system");

    companion object {
        private val map = values().associateBy(UiPref::value)

        fun fromString(type: String) = map[type]
    }
}