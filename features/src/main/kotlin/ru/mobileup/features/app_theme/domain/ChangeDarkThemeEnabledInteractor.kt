package ru.mobileup.features.app_theme.domain

import ru.mobileup.features.app_theme.data.AppThemeStorage

class ChangeDarkThemeEnabledInteractor(private val appThemeStorage: AppThemeStorage) {

    fun execute(isEnabled: Boolean) {
        appThemeStorage.updateDarkThemeEnabled(isEnabled)
    }
}