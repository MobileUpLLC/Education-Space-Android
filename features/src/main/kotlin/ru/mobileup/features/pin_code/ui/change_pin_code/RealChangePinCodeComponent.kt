package ru.mobileup.features.pin_code.ui.change_pin_code

import android.os.Parcelable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.router.replaceCurrent
import com.arkivanov.decompose.router.router
import ru.mobileup.core.ComponentFactory
import ru.mobileup.features.R
import ru.mobileup.core.message.domain.MessageData
import ru.mobileup.core.message.data.MessageService
import ru.mobileup.core.utils.toComposeState
import ru.mobileup.features.pin_code.createCheckPinCodeComponent
import ru.mobileup.features.pin_code.createCreatingPinCodeComponent
import ru.mobileup.features.pin_code.ui.check_pin_code.CheckPinCodeComponent
import ru.mobileup.features.pin_code.ui.check_pin_code.CheckPinCodeMode
import ru.mobileup.features.pin_code.ui.create_pin_code.CreatePinCodeComponent
import ru.mobileup.features.pin_code.ui.create_pin_code.CreatePinCodeMode
import kotlinx.parcelize.Parcelize
import me.aartikov.sesame.localizedstring.LocalizedString

class RealChangePinCodeComponent(
    componentContext: ComponentContext,
    private val onOutput: (ChangePinCodeComponent.Output) -> Unit,
    private val componentFactory: ComponentFactory,
    private val messageService: MessageService
) : ComponentContext by componentContext, ChangePinCodeComponent {

    private val router = router<ChildConfig, ChangePinCodeComponent.Child>(
        initialConfiguration = ChildConfig.Check,
        handleBackButton = true,
        childFactory = ::createChild
    )

    override val routerState: RouterState<*, ChangePinCodeComponent.Child> by router.state.toComposeState(
        lifecycle
    )

    private fun createChild(
        config: ChildConfig,
        componentContext: ComponentContext
    ) = when (config) {
        is ChildConfig.Check -> ChangePinCodeComponent.Child.Check(
            componentFactory.createCheckPinCodeComponent(
                componentContext,
                ::onCheckPinCodeOutput,
                isForgetPinCodeButtonVisible = true,
                isFingerprintButtonVisible = false,
                isAttemptsCountCheck = false,
                mode = CheckPinCodeMode.Change
            )
        )
        is ChildConfig.Create -> ChangePinCodeComponent.Child.Create(
            componentFactory.createCreatingPinCodeComponent(
                componentContext,
                ::onCreatePinCodeOutput,
                CreatePinCodeMode.Change
            )
        )
    }

    private fun onCheckPinCodeOutput(output: CheckPinCodeComponent.Output) {
        when (output) {
            is CheckPinCodeComponent.Output.PinCodeChecked -> router.replaceCurrent(ChildConfig.Create)
            is CheckPinCodeComponent.Output.LoggedOut -> onOutput(ChangePinCodeComponent.Output.LoggedOut)
        }
    }

    private fun onCreatePinCodeOutput(output: CreatePinCodeComponent.Output) {
        if (output is CreatePinCodeComponent.Output.PinCodeCompleted) {
            messageService.showMessage(
                MessageData(
                    text = LocalizedString.resource(R.string.change_pincode_success_msg_text),
                    iconRes = ru.mobileup.core.R.drawable.ic_24_check
                )
            )
            onOutput(ChangePinCodeComponent.Output.PinCodeChanged)
        }
    }

    private sealed interface ChildConfig : Parcelable {

        @Parcelize
        object Check : ChildConfig

        @Parcelize
        object Create : ChildConfig
    }
}