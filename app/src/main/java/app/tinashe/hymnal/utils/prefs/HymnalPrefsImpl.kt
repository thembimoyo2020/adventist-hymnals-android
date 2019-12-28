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

package app.tinashe.hymnal.utils.prefs

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import app.tinashe.hymnal.BuildConfig
import app.tinashe.hymnal.data.model.constants.UiPref

class HymnalPrefsImpl constructor(val context: Context) : HymnalPrefs {

    private val prefs: SharedPreferences = context.getSharedPreferences(KEY_PREF_NAME, Context.MODE_PRIVATE)

    override fun getLanguage(): String = prefs.getString(PREF_LANGUAGE, "eng") as String

    override fun setLanguage(language: String) {
        prefs.edit()
                .putString(PREF_LANGUAGE, language)
                .apply()
    }

    override fun getLastHymnNumber(): Int = prefs.getInt(PREF_LAST_NUMBER, 0)

    override fun setLastHymnNumber(number: Int) {
        prefs.edit()
                .putInt(PREF_LAST_NUMBER, number)
                .apply()
    }

    override fun getUiPref(): UiPref {
        val value = prefs.getString(KEY_UI_THEME, UiPref.FOLLOW_SYSTEM.value)

        return value?.let {
            UiPref.fromString(it)
        } ?: UiPref.FOLLOW_SYSTEM
    }

    override fun setUiPref(pref: UiPref) {

        prefs.edit {
            putString(KEY_UI_THEME, pref.value)
        }
    }

    companion object {
        private const val KEY_PREF_NAME = "${BuildConfig.APPLICATION_ID}.prefs"
        private const val PREF_LANGUAGE = "LANGUAGE"
        private const val PREF_LAST_NUMBER = "LAST_NUMBER"
        private const val KEY_UI_THEME = "pref_ui_theme"
    }
}