package ru.mobileup.features.pin_code.ui.create_pin_code

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import ru.mobileup.core.ComponentFactory
import ru.mobileup.features.R
import ru.mobileup.core.error_handling.ErrorHandler
import ru.mobileup.core.utils.componentCoroutineScope
import ru.mobileup.core.error_handling.safeLaunch
import ru.mobileup.features.pin_code.createPinCodeComponent
import ru.mobileup.features.pin_code.domain.PinCode
import ru.mobileup.features.pin_code.domain.SavePinCodeInteractor
import ru.mobileup.features.pin_code.ui.pin_code.PinCodeComponent
import me.aartikov.sesame.localizedstring.LocalizedString

class RealCreatePinCodeComponent(
    componentContext: ComponentContext,
    componentFactory: ComponentFactory,
    private val onOutput: (CreatePinCodeComponent.Output) -> Unit,
    private val mode: CreatePinCodeMode,
    private val errorHandler: ErrorHandler,
    private val savePinCodeInteractor: SavePinCodeInteractor
) : ComponentContext by componentContext, CreatePinCodeComponent {

    private var state: CreatePinCodeState by mutableStateOf(CreatePinCodeState.FirstEntering)

    override val title: LocalizedString by derivedStateOf<LocalizedString> {
        return@derivedStateOf when (state) {
            is CreatePinCodeState.FirstEntering -> when (mode) {
                CreatePinCodeMode.New -> LocalizedString.resource(R.string.pincode_create_setup_title)
                CreatePinCodeMode.Change -> LocalizedString.resource(R.string.pincode_create_change_title)
            }
            is CreatePinCodeState.Repeat -> {
                when (mode) {
                    CreatePinCodeMode.New -> LocalizedString.resource(R.string.pincode_create_new_repeat_title)
                    CreatePinCodeMode.Change -> LocalizedString.resource(R.string.pincode_create_change_repeat_title)
                }
            }
        }
    }

    override val pinCodeComponent: PinCodeComponent = componentFactory.createPinCodeComponent(
        componentContext = childContext("pin_code"),
        onOutput = ::onPinCodeOutput
    )

    private val coroutineScope = componentCoroutineScope()

    init {
        backPressedHandler.register(::onBackPressed)
    }

    private fun onPinCodeOutput(output: PinCodeComponent.Output) {
        when (output) {
            is PinCodeComponent.Output.PinCodeEntered -> {
                val pincode = output.pinCode
                when (state) {
                    is CreatePinCodeState.FirstEntering -> {
                        state = CreatePinCodeState.Repeat(pincode)
                        pinCodeComponent.clearInput()
                    }
                    is CreatePinCodeState.Repeat -> {
                        if ((state as CreatePinCodeState.Repeat).enteredPinCode != pincode) {
                            pinCodeComponent.showError(LocalizedString.resource(R.string.pincode_create_error_not_match))
                        } else {
                            coroutineScope.safeLaunch(errorHandler) {
                                savePinCodeInteractor.execute(pincode)
                                onOutput(CreatePinCodeComponent.Output.PinCodeCompleted)
                            }
                        }
                    }
                }
            }

            is PinCodeComponent.Output.PinCodeEnteringAfterError -> {
                if (state is CreatePinCodeState.Repeat) {
                    state = CreatePinCodeState.FirstEntering
                }
            }

            PinCodeComponent.Output.PinCodeForgotten -> Unit // nothing

            PinCodeComponent.Output.FingerprintAuthentication -> Unit // nothing
        }
    }

    private fun onBackPressed(): Boolean {
        return if (state is CreatePinCodeState.Repeat) {
            state = CreatePinCodeState.FirstEntering
            pinCodeComponent.clearInput()
            true
        } else {
            onOutput(CreatePinCodeComponent.Output.PinCodeCanceled)
            false
        }
    }

    sealed interface CreatePinCodeState {

        object FirstEntering : CreatePinCodeState

        class Repeat(val enteredPinCode: PinCode) : CreatePinCodeState
    }
}