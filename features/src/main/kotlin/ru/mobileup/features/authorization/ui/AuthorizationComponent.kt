package ru.mobileup.features.authorization.ui

import com.arkivanov.decompose.router.RouterState
import ru.mobileup.features.pin_code.ui.create_pin_code.CreatePinCodeComponent

interface AuthorizationComponent {

    val routerState: RouterState<*, Child>

    sealed interface Child {

        class CreateNewPinCode(val component: CreatePinCodeComponent) : Child
    }

    sealed interface Output {
        object AuthorizationCompleted : Output
    }
}