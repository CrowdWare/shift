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

object PersistanceManager{
    private const val LANGUAGE_INDEX_PREF = "language_index_pref"
    private const val LANGUAGE_CODE_PREF = "language_code_pref"
    private const val DELETE_WARNING_SEEN = "delete_warning_pref"
    private const val APP_PREFS = "app_prefs"

    fun hasSeenDeleteWarning(context: Context): Boolean {
        val preferences = context.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE)
        val has = preferences.getString(DELETE_WARNING_SEEN, "false")
        return has == "true"
    }

    fun putHasSeenDeleteWarning(context: Context) {
        val preferences = context.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE)
        preferences.edit().putString(DELETE_WARNING_SEEN, "true").apply()
    }

    fun saveLanguageIndex(context: Context, language: Int) {
        val preferences = context.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE)
        preferences.edit().putInt(LANGUAGE_INDEX_PREF, language).apply()
    }

    fun getLanguageIndex(context: Context): Int {
        val preferences = context.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE)
        return preferences.getInt(LANGUAGE_INDEX_PREF, -1)
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