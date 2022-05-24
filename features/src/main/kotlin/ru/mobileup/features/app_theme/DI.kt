package ru.mobileup.features.app_theme

import com.arkivanov.decompose.ComponentContext
import ru.mobileup.features.app_theme.data.AppThemeStorage
import ru.mobileup.features.app_theme.data.AppThemeStorageImpl
import ru.mobileup.features.app_theme.domain.ChangeDarkThemeEnabledInteractor
import ru.mobileup.features.app_theme.domain.IsDarkThemeEnabledInteractor
import ru.mobileup.features.app_theme.ui.AppThemeComponent
import ru.mobileup.features.app_theme.ui.RealAppThemeComponent
import ru.mobileup.core.ComponentFactory
import ru.mobileup.core.storage.SharedPreferencesFactory
import org.koin.android.ext.koin.androidContext
import org.koin.core.component.get
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val appThemePrefsName = "app_theme_preferences"

val appThemeModule = module {
    single(named(appThemePrefsName)) {
        get<SharedPreferencesFactory>().createPreferences(
            androidContext(),
            appThemePrefsName
        )
    }
    single<AppThemeStorage> { AppThemeStorageImpl(get(named(appThemePrefsName))) }
    factory { IsDarkThemeEnabledInteractor(get()) }
    factory { ChangeDarkThemeEnabledInteractor(get()) }
}

fun ComponentFactory.createAppThemeComponent(
    componentContext: ComponentContext,
): AppThemeComponent {
    return RealAppThemeComponent(componentContext, get(), get())
}