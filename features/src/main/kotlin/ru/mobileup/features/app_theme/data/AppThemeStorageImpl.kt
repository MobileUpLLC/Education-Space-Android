package ru.mobileup.features.app_theme.data

import android.content.SharedPreferences
import androidx.core.content.edit

class AppThemeStorageImpl(private val prefs: SharedPreferences) : AppThemeStorage {

    companion object {
        const val DARK_THEME_ENABLED_KEY = "dark_theme_enabled_key"
    }

    override fun isDarkThemeEnabled(): Boolean {
        return prefs.getBoolean(DARK_THEME_ENABLED_KEY, false)
    }

    override fun updateDarkThemeEnabled(isEnabled: Boolean) {
        prefs.edit { putBoolean(DARK_THEME_ENABLED_KEY, isEnabled) }
    }
}