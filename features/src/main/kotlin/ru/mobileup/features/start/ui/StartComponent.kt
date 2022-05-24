package ru.mobileup.features.start.ui

interface StartComponent {

    sealed interface Output {
        object PinCodeRequested : Output

        object HomeRequested : Output
    }
}