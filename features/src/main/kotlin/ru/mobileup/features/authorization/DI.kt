package ru.mobileup.features.authorization

import com.arkivanov.decompose.ComponentContext
import ru.mobileup.core.ComponentFactory
import ru.mobileup.features.authorization.ui.AuthorizationComponent
import ru.mobileup.features.authorization.ui.RealAuthorizationComponent
import org.koin.core.component.get

fun ComponentFactory.createAuthorizationComponent(
    componentContext: ComponentContext,
    onOutput: (AuthorizationComponent.Output) -> Unit
): AuthorizationComponent {
    return RealAuthorizationComponent(componentContext, onOutput, get(), get())
}