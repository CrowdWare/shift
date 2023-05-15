/****************************************************************************
 * Copyright (C) 2023 CrowdWare
 *
 * This file is part of SHIFT.
 *
 *  SHIFT is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  SHIFT is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with SHIFT.  If not, see <http://www.gnu.org/licenses/>.
 *
 ****************************************************************************/
package at.crowdware.shift.logic

import android.content.Context
import at.crowdware.shift.JoinData

object PersistanceManager{
    private const val NAME_PREF = "name_pref"
    private const val FRIEND_PREF = "friend_pref"
    private const val COUNTRY_PREF = "country_pref"
    private const val LANGUAGE_PREF = "language_pref"
    private const val LANGUAGE_CODE_PREF = "language_code_pref"
    private const val APP_PREFS = "app_prefs"

    fun saveJoinData(context: Context, name: String, friend: String, country: Int, language: Int) {
        val preferences = context.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE)
        preferences.edit().putString(NAME_PREF, name).apply()
        preferences.edit().putString(FRIEND_PREF, friend).apply()
        preferences.edit().putInt(COUNTRY_PREF, country).apply()
        preferences.edit().putInt(LANGUAGE_PREF, language).apply()
    }

    fun readJoinData(context: Context): JoinData {
        val preferences = context.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE)
        val name = preferences.getString(NAME_PREF, "")
        val friend = preferences.getString(FRIEND_PREF, "")
        val country = preferences.getInt(COUNTRY_PREF, -1)
        val language = preferences.getInt(LANGUAGE_PREF, -1)
        return JoinData(name!!, friend!!, country, language)
    }

    fun getLanguage(context: Context): String? {
        val preferences = context.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE)
        return preferences.getString(LANGUAGE_CODE_PREF, "")
    }

    fun setLanguageCode(context: Context, language: String) {
        val preferences = context.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE)
        preferences.edit().putString(LANGUAGE_CODE_PREF, language).apply()
    }
}