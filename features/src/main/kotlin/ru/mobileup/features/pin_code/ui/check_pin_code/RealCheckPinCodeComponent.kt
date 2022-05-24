package ru.mobileup.features.pin_code.ui.check_pin_code

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import com.arkivanov.essenty.lifecycle.doOnCreate
import ru.mobileup.core.ComponentFactory
import ru.mobileup.features.R
import ru.mobileup.core.biometric.BiometricAuthenticationResult
import ru.mobileup.core.biometric.BiometricService
import ru.mobileup.core.error_handling.ErrorHandler
import ru.mobileup.core.utils.componentCoroutineScope
import ru.mobileup.core.error_handling.safeLaunch
import ru.mobileup.features.pin_code.createPinCodeComponent
import ru.mobileup.features.pin_code.domain.GetPinCodeInteractor
import ru.mobileup.features.pin_code.domain.PinCode
import ru.mobileup.features.pin_code.ui.pin_code.PinCodeComponent
import ru.mobileup.features.settings.domain.LogoutInteractor
import kotlinx.coroutines.flow.collectLatest
import me.aartikov.sesame.dialog.DialogControl
import me.aartikov.sesame.localizedstring.LocalizedString
import ru.mobileup.core.widget.dialog.AlertDialogData
import ru.mobileup.core.widget.dialog.DialogResult

class RealCheckPinCodeComponent(
    componentContext: ComponentContext,
    isForgetPinCodeButtonVisible: Boolean,
    isFingerprintButtonVisible: Boolean,
    private val isAttemptsCountCheck: Boolean,
    private val onOutput: (CheckPinCodeComponent.Output) -> Unit,
    mode: CheckPinCodeMode,
    componentFactory: ComponentFactory,
    private val getPinCodeInteractor: GetPinCodeInteractor,
    private val logoutInteractor: LogoutInteractor,
    private val errorHandler: ErrorHandler,
    private val biometricService: BiometricService
) : ComponentContext by componentContext, CheckPinCodeComponent {

    companion object {
        private const val ATTEMPTS_LIMIT = 3
    }

    private val coroutineScope = componentCoroutineScope()

    private var attemptsCount = 0

    override val title = when (mode) {
        CheckPinCodeMode.Confirm -> LocalizedString.resource(R.string.check_pincode_confirm_title)
        CheckPinCodeMode.Change -> LocalizedString.resource(R.string.check_pincode_change_title)
    }

    override val forgottenPinCodeDialogControl = DialogControl<AlertDialogData, DialogResult>()

    override val attemptsLimitDialogControl = DialogControl<AlertDialogData, DialogResult>()

    override val pinCodeComponent: PinCodeComponent =
        componentFactory.createPinCodeComponent(
            childContext("pincode"),
            ::onPinCodeOutput,
            isForgetPinCodeButtonVisible = isForgetPinCodeButtonVisible,
            isFingerprintButtonVisible = isFingerprintButtonVisible && biometricService.isFingerprintSupport()
        )

    init {
        lifecycle.doOnCreate {
            subscribeBiometricAuthenticationResult()
        }
    }

    private fun onPinCodeOutput(output: PinCodeComponent.Output) {
        when (output) {
            is PinCodeComponent.Output.PinCodeEntered -> checkEnteredPinCode(output.pinCode)
            is PinCodeComponent.Output.PinCodeForgotten -> showForgottenPinCodeDialog()
            is PinCodeComponent.Output.FingerprintAuthentication -> {
                coroutineScope.safeLaunch(errorHandler) {
                    biometricService.startFingerprintAuthenticate()
                }
            }
            PinCodeComponent.Output.PinCodeEnteringAfterError -> Unit // nothing
        }
    }

    private fun checkEnteredPinCode(pinCode: PinCode) = coroutineScope.safeLaunch(errorHandler) {
        if (pinCode == getPinCodeInteractor.execute()) {
            onOutput(CheckPinCodeComponent.Output.PinCodeChecked)
        } else {
            pinCodeComponent.showError(LocalizedString.resource(R.string.check_pincode_error))

            if (isAttemptsCountCheck) {
                attemptsCount++
                if (attemptsCount == ATTEMPTS_LIMIT) {
                    attemptsCount = 0
                    showAttemptsLimitDialog()
                }
            }
        }
    }

    private fun showForgottenPinCodeDialog() = coroutineScope.safeLaunch(errorHandler) {
        val data = AlertDialogData(
            title = LocalizedString.resource(R.string.check_pincode_forgotten_dialog_title),
            message = LocalizedString.resource(R.string.check_pincode_forgotten_dialog_msg),
            positiveButtonText = LocalizedString.resource(R.string.check_pincode_forgotten_dialog_positive_btn),
            dismissButtonText = LocalizedString.resource(ru.mobileup.core.R.string.common_cancel),
        )
        val result = forgottenPinCodeDialogControl.showForResult(data) ?: DialogResult.Cancel
        if (result == DialogResult.Confirm) {
            logoutInteractor.execute()
            onOutput(CheckPinCodeComponent.Output.LoggedOut)
        }
    }

    private fun showAttemptsLimitDialog() = coroutineScope.safeLaunch(errorHandler) {
        val data = AlertDialogData(
            title = LocalizedString.resource(R.string.check_pincode_attempts_limit_dialog_tittle),
            message = LocalizedString.resource(R.string.check_pincode_attempts_limit_dialog_msg),
            positiveButtonText = LocalizedString.resource(R.string.check_pincode_attempts_limit_dialog_btn)
        )
        attemptsLimitDialogControl.show(data)
    }

    private fun subscribeBiometricAuthenticationResult() {
        coroutineScope.safeLaunch(errorHandler) {
            biometricService.fingerprintResultFlow.collectLatest {
                if (it is BiometricAuthenticationResult.Succeeded) {
                    onOutput(CheckPinCodeComponent.Output.PinCodeChecked)
                }
            }
        }
    }
}