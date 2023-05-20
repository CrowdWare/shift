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
import android.content.res.Configuration
import android.content.res.Resources
import androidx.compose.ui.platform.LocalContext
import at.crowdware.shift.R
import java.util.*

object LocaleManager {
    private var currentLocale: Locale? = null
    private var language: String? = ""
    private var index: Int = -1
    private val language_codes = listOf("en", "de", "es", "fr", "pt", "eo")
    private val languages: MutableList<String> = mutableListOf()

    fun setLocale(context: Context, lang: String): Context {
        language = lang
        index = language_codes.indexOf(language)
        PersistanceManager.setLanguageCode(context, lang)
        return updateResources(context, lang)
    }

    fun setLocale(context: Context, index: Int): Context {
        if(index < 0 || index > 5) return context
        language = language_codes[index]
        PersistanceManager.setLanguageCode(context, language!!)
        return updateResources(context, language!!)
    }

    fun getLanguage(): String { return language!! }

    fun getLanguageIndex(): Int { return index }

    fun getLanguages(): List<String> {
        return languages.toList()
    }

    fun init(context: Context, resources: Resources){
        languages.clear()
        languages.add(resources.getString(R.string.language_english))
        languages.add(resources.getString(R.string.language_german))
        languages.add(resources.getString(R.string.language_spanish))
        languages.add(resources.getString(R.string.language_french))
        languages.add(resources.getString(R.string.language_portugues))
        languages.add(resources.getString(R.string.language_esperanto))

        if (currentLocale == null) {
            language = PersistanceManager.getLanguage(context)
            if(language.isNullOrEmpty())
                language = Resources.getSystem().configuration.locales[0].language
            index = language_codes.indexOf(language)
            if(!language.isNullOrEmpty()) {
                currentLocale = Locale(language!!)
            }
        }
    }

    fun updateResources(context: Context, language: String): Context {
        if(language.isEmpty()) // initial no changes
            return context
        val locale = Locale(language)
        Locale.setDefault(locale)

        val configuration = context.resources.configuration
        configuration.setLocale(locale)
        val contextWithUpdatedLocale = context.createConfigurationContext(configuration)

        currentLocale = locale
        return contextWithUpdatedLocale
    }

    fun wrapContext(context: Context): Context {
        if (currentLocale == null)
            init(context, context.resources)
        if(currentLocale != null) {
            Locale.setDefault(currentLocale!!)
            val newConfig = Configuration()
            newConfig.setLocale(currentLocale)
            return context.createConfigurationContext(newConfig)
        }
        return context
    }
}
