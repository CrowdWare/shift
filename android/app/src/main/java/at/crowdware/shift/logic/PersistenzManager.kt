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
    private const val LANGUAGE_CODE_PREF = "language_code_pref"
    private const val DELETE_WARNING_SEEN = "delete_warning_seen_pref"
    private const val DISPLAY_SCOOPING = "display_scooping_pref"
    private const val USE_WEBSERVICE = "use_webservice_pref"
    private const val HAS_READ_THE_BOOK = "has_read_the_book_pref"
    private const val APP_PREFS = "app_prefs"

    fun hasSeenDeleteWarning(context: Context): Boolean {
        val preferences = context.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE)
        return preferences.getBoolean(DELETE_WARNING_SEEN, false)
    }

    fun putHasSeenDeleteWarning(context: Context) {
        val preferences = context.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE)
        preferences.edit().putBoolean(DELETE_WARNING_SEEN, true).apply()
    }

    fun getLanguage(context: Context): String? {
        val preferences = context.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE)
        return preferences.getString(LANGUAGE_CODE_PREF, "")
    }

    fun setLanguageCode(context: Context, language: String) {
        val preferences = context.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE)
        preferences.edit().putString(LANGUAGE_CODE_PREF, language).apply()
    }

    fun getDisplayScooping(context: Context): Boolean {
        val preferences = context.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE)
        return preferences.getBoolean(DISPLAY_SCOOPING, true)
    }

    fun setDisplayScooping(context: Context, displaMillis: Boolean) {
        val preferences = context.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE)
        preferences.edit().putBoolean(DISPLAY_SCOOPING, displaMillis).apply()
    }

    fun getUseWebservice(context: Context): Boolean {
        val preferences = context.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE)
        return preferences.getBoolean(USE_WEBSERVICE, true)
    }

    fun setUseWebservice(context: Context, useWebService: Boolean) {
        val preferences = context.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE)
        preferences.edit().putBoolean(USE_WEBSERVICE, useWebService).apply()
    }

    fun getHasReadTheBook(context: Context): Boolean {
        val preferences = context.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE)
        return preferences.getBoolean(HAS_READ_THE_BOOK, false)
    }

    fun setHasReadTheBook(context: Context, hasReadBook: Boolean) {
        val preferences = context.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE)
        preferences.edit().putBoolean(HAS_READ_THE_BOOK, hasReadBook).apply()
    }
}
