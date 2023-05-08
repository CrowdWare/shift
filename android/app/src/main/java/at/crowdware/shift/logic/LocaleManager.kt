package at.crowdware.shift.logic

import android.content.Context
import android.content.res.Configuration
import java.util.*

object LocaleManager {
    private const val LANGUAGE_PREF = "language_pref"
    private var currentLocale: Locale? = null
    private var language: String? = ""

    fun setLocale(context: Context, language: String): Context {
        persistLanguagePreference(context, language)
        return updateResources(context, language)
    }

    fun getLanguage(): String { return language!! }

    fun init(context: Context){
        if (currentLocale == null) {
            val preferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
            language = preferences.getString(LANGUAGE_PREF, "")
            if(!language.isNullOrEmpty()) {
                currentLocale = Locale(language!!)
            }
        }
    }

    private fun persistLanguagePreference(context: Context, language: String) {
        val preferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        preferences.edit().putString(LANGUAGE_PREF, language).apply()
    }

    fun updateResources(context: Context, language: String): Context {
        if(language.isEmpty()) // initial no changes
            return context
        val locale = Locale(language)
        Locale.setDefault(locale)

        val resources = context.resources
        val configuration = context.resources.configuration //Configuration(resources.configuration)
        configuration.setLocale(locale)
        val contextWithUpdatedLocale = context.createConfigurationContext(configuration)

        currentLocale = locale
        return contextWithUpdatedLocale
    }

    fun wrapContext(context: Context): Context {
        if (currentLocale == null)
            init(context)
        Locale.setDefault(currentLocale)
        val newConfig = Configuration()
        newConfig.setLocale(currentLocale)
        return context.createConfigurationContext(newConfig)
    }
}
