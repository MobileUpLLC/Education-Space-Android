package ru.mobileup.features.app_theme.ui

import ru.mobileup.core.theme.ThemeType

interface AppThemeComponent {

    val themeType: ThemeType

    fun onThemeChange()
}