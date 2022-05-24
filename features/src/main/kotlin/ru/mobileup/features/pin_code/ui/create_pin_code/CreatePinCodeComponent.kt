package ru.mobileup.features.pin_code.ui.create_pin_code

import ru.mobileup.features.pin_code.ui.pin_code.PinCodeComponent
import me.aartikov.sesame.localizedstring.LocalizedString

interface CreatePinCodeComponent {

    val pinCodeComponent: PinCodeComponent

    val title: LocalizedString

    sealed interface Output {

        object PinCodeCompleted : Output

        object PinCodeCanceled : Output
    }
}