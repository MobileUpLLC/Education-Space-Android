package ru.mobileup.features.pin_code.ui.pin_code

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.arkivanov.decompose.ComponentContext
import ru.mobileup.features.pin_code.domain.PinCode
import me.aartikov.sesame.localizedstring.LocalizedString

class RealPinCodeComponent(
    componentContext: ComponentContext,
    isForgetPinCodeButtonVisible: Boolean,
    isBiometricButtonVisible: Boolean,
    private val onOutput: (PinCodeComponent.Output) -> Unit
) : ComponentContext by componentContext, PinCodeComponent {

    var pinCode: String by mutableStateOf("")
        private set

    override var progress: PinCodeComponent.ProgressState by mutableStateOf(
        PinCodeComponent.ProgressState.Progress(0)
    )
        private set

    override val isForgotButtonVisible = isForgetPinCodeButtonVisible

    override val isFingerprintButtonVisible = isBiometricButtonVisible

    override val isBackspaceVisible: Boolean by derivedStateOf { pinCode.isNotEmpty() }

    override fun onDigitClick(digit: String) {
        when {
            progress is PinCodeComponent.ProgressState.Error -> {
                pinCode = digit
            }
            pinCode.length != PinCode.PIN_CODE_LENGTH -> {
                pinCode += digit
            }
        }
        updateProgress()
        checkIsPinCodeEnteringComplete()
    }

    override fun clearInput() {
        pinCode = ""
        updateProgress()
    }

    override fun showError(message: LocalizedString) {
        progress = PinCodeComponent.ProgressState.Error(message)
    }

    override fun onBackspaceClick() {
        if (pinCode.isNotEmpty()) {
            pinCode = pinCode.dropLast(1)
        }
        updateProgress()
    }

    override fun onForgotPinCodeClick() {
        onOutput(PinCodeComponent.Output.PinCodeForgotten)
    }

    override fun onFingerprintClick() {
        onOutput(PinCodeComponent.Output.FingerprintAuthentication)
    }

    private fun checkIsPinCodeEnteringComplete() {
        if (pinCode.length == PinCode.PIN_CODE_LENGTH) {
            onOutput(PinCodeComponent.Output.PinCodeEntered(PinCode(pinCode)))
        }
    }

    private fun updateProgress() {
        if (progress is PinCodeComponent.ProgressState.Error) {
            onOutput(PinCodeComponent.Output.PinCodeEnteringAfterError)
        }
        progress = PinCodeComponent.ProgressState.Progress(pinCode.length)
    }
}