package ru.mobileup.features.pin_code.ui.pin_code_protection

interface PinCodeProtectionComponent {

    sealed interface Output {
        object CheckPinCodeRequested : Output
    }
}