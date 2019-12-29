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

package app.tinashe.hymnal.data.model

data class HymnalCollection(val name: String,
                            val hymnals: List<String> = emptyList(),
                            val order: Int = -1,
                            var id: String = "") : Comparable<HymnalCollection> {

    constructor() : this("")

    override fun compareTo(other: HymnalCollection): Int {
        return order.compareTo(other.order)
    }
}