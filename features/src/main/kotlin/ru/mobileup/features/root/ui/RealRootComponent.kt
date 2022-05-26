package ru.mobileup.features.root.ui

import android.os.Parcelable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.router.pop
import com.arkivanov.decompose.router.push
import com.arkivanov.decompose.router.router
import ru.mobileup.features.app_theme.createAppThemeComponent
import ru.mobileup.core.ComponentFactory
import ru.mobileup.features.authorization.createAuthorizationComponent
import ru.mobileup.features.authorization.ui.AuthorizationComponent
import ru.mobileup.core.utils.replaceAll
import ru.mobileup.core.utils.toComposeState
import ru.mobileup.features.home.createHomeComponent
import ru.mobileup.features.home.ui.HomeComponent
import ru.mobileup.core.message.createMessagesComponent
import ru.mobileup.features.pin_code.createChangePinCodeComponent
import ru.mobileup.features.pin_code.ui.change_pin_code.ChangePinCodeComponent
import ru.mobileup.features.start.createStartComponent
import ru.mobileup.features.start.ui.StartComponent
import kotlinx.parcelize.Parcelize
import ru.mobileup.features.pin_code.createPinCodeProtectionComponent
import ru.mobileup.features.pin_code.ui.pin_code_protection.PinCodeProtectionComponent

class RealRootComponent(
    componentContext: ComponentContext,
    private val componentFactory: ComponentFactory
) : ComponentContext by componentContext, RootComponent {

    private val router = router<ChildConfig, RootComponent.Child>(
        initialConfiguration = ChildConfig.Start,
        handleBackButton = true,
        childFactory = ::createChild
    )

    override val routerState: RouterState<*, RootComponent.Child> by router.state.toComposeState(
        lifecycle
    )

    override val appThemeComponent =
        componentFactory.createAppThemeComponent(childContext(key = "app_theme"))

    override val messageComponent =
        componentFactory.createMessagesComponent(childContext(key = "message"))

    @Suppress("unused")
    private val pinCodeProtectionComponent = componentFactory.createPinCodeProtectionComponent(
        childContext(key = "pin_code_protection"),
        ::onPinCodeProtectionOutput
    )

    private fun createChild(config: ChildConfig, componentContext: ComponentContext) =
        when (config) {
            is ChildConfig.Start -> RootComponent.Child.Start(
                componentFactory.createStartComponent(
                    componentContext,
                    ::onStartOutput
                )
            )

            is ChildConfig.Authorization -> RootComponent.Child.Authorization(
                componentFactory.createAuthorizationComponent(
                    componentContext,
                    ::onAuthorizationOutput
                )
            )

            is ChildConfig.Home -> RootComponent.Child.Home(
                componentFactory.createHomeComponent(
                    componentContext,
                    ::onHomeOutput,
                    config.initialScreen
                )
            )

            is ChildConfig.ChangePinCode -> RootComponent.Child.ChangePinCode(
                componentFactory.createChangePinCodeComponent(
                    componentContext,
                    ::onChangePinCodeOutput
                )
            )
        }

    private fun onHomeOutput(output: HomeComponent.Output) {
        when (output) {
            is HomeComponent.Output.PinCodeChangingRequested -> router.push(ChildConfig.ChangePinCode)

            is HomeComponent.Output.LoggedOut -> router.replaceAll(ChildConfig.Start)

            is HomeComponent.Output.ThemeChanged -> appThemeComponent.onThemeChange()
        }
    }

    private fun onAuthorizationOutput(output: AuthorizationComponent.Output) {
        when (output) {
            is AuthorizationComponent.Output.AuthorizationCompleted -> router.replaceAll(
                ChildConfig.Home(HomeComponent.InitialScreen.UpcomingLaunchers)
            )
        }
    }

    private fun onStartOutput(output: StartComponent.Output) {
        when (output) {
            is StartComponent.Output.HomeRequested -> router.replaceAll(
                ChildConfig.Home(HomeComponent.InitialScreen.CheckPinCode)
            )

            is StartComponent.Output.PinCodeRequested -> router.replaceAll(ChildConfig.Authorization)
        }
    }

    private fun onChangePinCodeOutput(output: ChangePinCodeComponent.Output) {
        when (output) {
            is ChangePinCodeComponent.Output.PinCodeChanged -> router.pop()
            is ChangePinCodeComponent.Output.LoggedOut -> router.replaceAll(ChildConfig.Authorization)
        }
    }

    private fun onPinCodeProtectionOutput(output: PinCodeProtectionComponent.Output) {
        // Set up routing for your project
        when (output) {
            is PinCodeProtectionComponent.Output.CheckPinCodeRequested -> router.replaceAll(
                ChildConfig.Home(HomeComponent.InitialScreen.CheckPinCode)
            )
        }
    }

    private sealed interface ChildConfig : Parcelable {

        @Parcelize
        object Start : ChildConfig

        @Parcelize
        object Authorization : ChildConfig

        @Parcelize
        class Home(val initialScreen: HomeComponent.InitialScreen) : ChildConfig

        @Parcelize
        object ChangePinCode : ChildConfig
    }
}