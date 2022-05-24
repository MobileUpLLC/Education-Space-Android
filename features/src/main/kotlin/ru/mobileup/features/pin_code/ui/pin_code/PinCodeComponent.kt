package ru.mobileup.features.pin_code.ui.pin_code

import ru.mobileup.features.pin_code.domain.PinCode
import me.aartikov.sesame.localizedstring.LocalizedString

interface PinCodeComponent {

    val progress: ProgressState

    val isBackspaceVisible: Boolean

    val isForgotButtonVisible: Boolean

    val isFingerprintButtonVisible: Boolean

    fun onDigitClick(digit: String)

    fun onBackspaceClick()

    sealed interface ProgressState {
        data class Progress(val count: Int) : ProgressState
        data class Error(val message: LocalizedString) : ProgressState
    }

    fun clearInput()

    fun showError(message: LocalizedString)

    fun onForgotPinCodeClick()

    fun onFingerprintClick()

    sealed interface Output {
        data class PinCodeEntered(val pinCode: PinCode) : Output

        object PinCodeForgotten : Output

        object FingerprintAuthentication : Output

        object PinCodeEnteringAfterError : Output
    }
}