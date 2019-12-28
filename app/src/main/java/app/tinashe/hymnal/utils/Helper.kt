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

package app.tinashe.hymnal.utils

import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate
import app.tinashe.hymnal.data.model.constants.UiPref

object Helper {

    /**
     * Switch to theme based on [UiPref] selected by user
     */
    fun switchToTheme(pref: UiPref) {
        val theme = when (pref) {
            UiPref.DAY -> AppCompatDelegate.MODE_NIGHT_NO
            UiPref.NIGHT -> AppCompatDelegate.MODE_NIGHT_YES
            UiPref.FOLLOW_SYSTEM -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            UiPref.BATTERY_SAVER -> AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY
        }

        AppCompatDelegate.setDefaultNightMode(theme)
    }

    /**
     * Returns true is app is in dark theme
     */
    fun isDarkTheme(context: Context): Boolean {
        val resources = context.resources
        return resources.configuration.uiMode and
                Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
    }
}