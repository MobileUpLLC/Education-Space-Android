package ru.mobileup.features.settings

import com.arkivanov.decompose.ComponentContext
import ru.mobileup.core.ComponentFactory
import ru.mobileup.features.settings.domain.LogoutInteractor
import ru.mobileup.features.settings.ui.RealSettingsComponent
import ru.mobileup.features.settings.ui.SettingsComponent
import org.koin.core.component.get
import org.koin.dsl.module

val settingsModule = module {
    factory { LogoutInteractor(get()) }
}

fun ComponentFactory.createSettingsComponent(
    componentContext: ComponentContext,
    onOutput: (SettingsComponent.Output) -> Unit
): SettingsComponent {
    return RealSettingsComponent(componentContext, onOutput, get(), get(), get(), get())
}