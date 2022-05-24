package ru.mobileup.features.authorization.ui

import android.os.Parcelable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.router.router
import ru.mobileup.core.ComponentFactory
import ru.mobileup.core.exit.ExitService
import ru.mobileup.core.utils.toComposeState
import ru.mobileup.features.pin_code.createCreatingPinCodeComponent
import ru.mobileup.features.pin_code.ui.create_pin_code.CreatePinCodeComponent
import ru.mobileup.features.pin_code.ui.create_pin_code.CreatePinCodeMode
import kotlinx.parcelize.Parcelize

class RealAuthorizationComponent(
    componentContext: ComponentContext,
    private val onOutput: (AuthorizationComponent.Output) -> Unit,
    private val componentFactory: ComponentFactory,
    private val exitService: ExitService
) : ComponentContext by componentContext, AuthorizationComponent {

    private val router = router(
        initialConfiguration = ChildConfig.CreateNewPinCode,
        handleBackButton = true,
        childFactory = ::createChild
    )

    override val routerState: RouterState<*, AuthorizationComponent.Child> by router.state.toComposeState(
        lifecycle
    )

    private fun createChild(
        config: ChildConfig,
        componentContext: ComponentContext
    ): AuthorizationComponent.Child =
        when (config) {
            is ChildConfig.CreateNewPinCode -> AuthorizationComponent.Child.CreateNewPinCode(
                componentFactory.createCreatingPinCodeComponent(
                    componentContext,
                    ::onPinCodeOutput,
                    CreatePinCodeMode.New
                )
            )
        }

    private fun onPinCodeOutput(output: CreatePinCodeComponent.Output) {
        when (output) {
            is CreatePinCodeComponent.Output.PinCodeCompleted -> onOutput(
                AuthorizationComponent.Output.AuthorizationCompleted
            )
            is CreatePinCodeComponent.Output.PinCodeCanceled -> exitService.closeApp()
        }
    }

    private sealed interface ChildConfig : Parcelable {

        @Parcelize
        object CreateNewPinCode : ChildConfig
    }
}