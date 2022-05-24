package ru.mobileup.education_space

import ru.mobileup.core.coreModule
import ru.mobileup.features.app_theme.appThemeModule
import ru.mobileup.features.launchers.launchersModule
import ru.mobileup.features.pin_code.pinCodeModule
import ru.mobileup.features.settings.settingsModule
import ru.mobileup.features.solar_system.solarSystemModule

val allModules = listOf(
    coreModule(BuildConfig.BACKEND_URL),
    pinCodeModule,
    launchersModule,
    appThemeModule,
    settingsModule,
    solarSystemModule
)