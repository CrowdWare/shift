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
        if(currentLocale != null) {
            Locale.setDefault(currentLocale)
            val newConfig = Configuration()
            newConfig.setLocale(currentLocale)
            return context.createConfigurationContext(newConfig)
        }
        return context
    }
}
