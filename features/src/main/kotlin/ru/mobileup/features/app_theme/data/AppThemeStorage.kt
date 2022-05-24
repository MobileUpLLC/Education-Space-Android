package ru.mobileup.features.app_theme.data

interface AppThemeStorage {

    fun isDarkThemeEnabled(): Boolean

    fun updateDarkThemeEnabled(isEnabled: Boolean)
}