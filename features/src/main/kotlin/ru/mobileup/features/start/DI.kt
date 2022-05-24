package ru.mobileup.features.start

import com.arkivanov.decompose.ComponentContext
import ru.mobileup.core.ComponentFactory
import ru.mobileup.features.start.ui.RealStartComponent
import ru.mobileup.features.start.ui.StartComponent
import org.koin.core.component.get

fun ComponentFactory.createStartComponent(
    componentContext: ComponentContext,
    onOutput: (StartComponent.Output) -> Unit
): StartComponent {
    return RealStartComponent(componentContext, onOutput, get(), get())
}