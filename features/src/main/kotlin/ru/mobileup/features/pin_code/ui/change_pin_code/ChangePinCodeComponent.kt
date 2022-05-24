package ru.mobileup.features.pin_code.ui.change_pin_code

import com.arkivanov.decompose.router.RouterState
import ru.mobileup.features.pin_code.ui.check_pin_code.CheckPinCodeComponent
import ru.mobileup.features.pin_code.ui.create_pin_code.CreatePinCodeComponent

interface ChangePinCodeComponent {

    val routerState: RouterState<*, Child>

    sealed interface Child {

        class Check(val component: CheckPinCodeComponent) : Child

        class Create(val component: CreatePinCodeComponent) : Child
    }

    sealed interface Output {

        object PinCodeChanged : Output

        object LoggedOut : Output
    }
}
