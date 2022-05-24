package ru.mobileup.features.app_theme.domain

import ru.mobileup.features.app_theme.data.AppThemeStorage

class IsDarkThemeEnabledInteractor(private val appThemeStorage: AppThemeStorage) {

    fun execute(): Boolean {
        return appThemeStorage.isDarkThemeEnabled()
    }
}