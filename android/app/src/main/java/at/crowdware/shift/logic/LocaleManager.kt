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
import java.util.*

object LocaleManager {
    private var currentLocale: Locale? = null
    private var language: String? = ""

    fun setLocale(context: Context, language: String): Context {
        this.language = language
        PersistanceManager.setLanguageCode(context, language)
        println("lang: $language")
        return updateResources(context, language)
    }

    fun getLanguage(): String { return language!! }

    fun init(context: Context){
        if (currentLocale == null) {
            language = PersistanceManager.getLanguage(context)
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
            init(context)
        if(currentLocale != null) {
            Locale.setDefault(currentLocale!!)
            val newConfig = Configuration()
            newConfig.setLocale(currentLocale)
            return context.createConfigurationContext(newConfig)
        }
        return context
    }
}
