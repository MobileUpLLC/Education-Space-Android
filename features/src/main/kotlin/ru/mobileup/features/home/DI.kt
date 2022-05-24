package ru.mobileup.features.home

import com.arkivanov.decompose.ComponentContext
import ru.mobileup.core.ComponentFactory
import ru.mobileup.features.home.ui.HomeComponent
import ru.mobileup.features.home.ui.RealHomeComponent
import org.koin.core.component.get

fun ComponentFactory.createHomeComponent(
    componentContext: ComponentContext,
    onOutput: (HomeComponent.Output) -> Unit,
    initialScreen: HomeComponent.InitialScreen
): HomeComponent {
    return RealHomeComponent(componentContext, onOutput, initialScreen, get())
}