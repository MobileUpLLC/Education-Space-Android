package ru.mobileup.features.home.ui

import android.os.Parcelable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.*
import ru.mobileup.core.ComponentFactory
import ru.mobileup.core.utils.currentInstance
import ru.mobileup.core.utils.toComposeState
import ru.mobileup.features.launchers.createUpcomingLaunchersComponent
import ru.mobileup.features.pin_code.createCheckPinCodeComponent
import ru.mobileup.features.pin_code.ui.check_pin_code.CheckPinCodeComponent
import ru.mobileup.features.pin_code.ui.check_pin_code.CheckPinCodeMode
import ru.mobileup.features.settings.createSettingsComponent
import ru.mobileup.features.settings.ui.SettingsComponent
import ru.mobileup.features.solar_system.createSolarSystemPlanetsComponent
import kotlinx.parcelize.Parcelize

class RealHomeComponent(
    componentContext: ComponentContext,
    private val onOutput: (HomeComponent.Output) -> Unit,
    initialScreen: HomeComponent.InitialScreen,
    private val componentFactory: ComponentFactory
) : ComponentContext by componentContext, HomeComponent {

    private val router = router(
        initialConfiguration = when (initialScreen) {
            is HomeComponent.InitialScreen.CheckPinCode -> ChildConfig.CheckPinCode
            is HomeComponent.InitialScreen.UpcomingLaunchers -> ChildConfig.UpcomingLaunchers
        },
        handleBackButton = true,
        childFactory = ::createChild
    )

    override val routerState: RouterState<*, HomeComponent.Child> by router.state.toComposeState(
        lifecycle
    )

    override val isBottomBarVisible by derivedStateOf {
        val currentInstance = routerState.currentInstance
        currentInstance !is HomeComponent.Child.CheckPinCode
    }

    override fun onPageSelected(page: HomeComponent.Page) {
        val configuration = when (page) {
            HomeComponent.Page.UpcomingLaunchers -> ChildConfig.UpcomingLaunchers
            HomeComponent.Page.Settings -> ChildConfig.Settings
            HomeComponent.Page.SolarSystem -> ChildConfig.SolarSystem
        }
        router.bringToFront(configuration)
    }

    private fun createChild(
        config: ChildConfig,
        componentContext: ComponentContext
    ): HomeComponent.Child =
        when (config) {
            is ChildConfig.CheckPinCode -> HomeComponent.Child.CheckPinCode(
                componentFactory.createCheckPinCodeComponent(
                    componentContext,
                    ::onCheckPinCodeOutput,
                    isForgetPinCodeButtonVisible = false,
                    isFingerprintButtonVisible = true,
                    isAttemptsCountCheck = true,
                    CheckPinCodeMode.Confirm
                )
            )

            is ChildConfig.UpcomingLaunchers -> HomeComponent.Child.UpcomingLaunchers(
                componentFactory.createUpcomingLaunchersComponent(componentContext)
            )

            is ChildConfig.Settings -> HomeComponent.Child.Settings(
                componentFactory.createSettingsComponent(componentContext, ::onSettingsOutput)
            )

            is ChildConfig.SolarSystem -> HomeComponent.Child.SolarSystem(
                componentFactory.createSolarSystemPlanetsComponent(componentContext)
            )
        }

    private fun onCheckPinCodeOutput(output: CheckPinCodeComponent.Output) {
        when (output) {
            is CheckPinCodeComponent.Output.PinCodeChecked -> router.replaceCurrent(ChildConfig.UpcomingLaunchers)

            is CheckPinCodeComponent.Output.LoggedOut -> onOutput(HomeComponent.Output.LoggedOut)
        }
    }

    private fun onSettingsOutput(output: SettingsComponent.Output) {
        when (output) {
            is SettingsComponent.Output.PinCodeChangingRequested -> {
                onOutput(HomeComponent.Output.PinCodeChangingRequested)
            }

            is SettingsComponent.Output.LoggedOut -> {
                onOutput(HomeComponent.Output.LoggedOut)
            }

            is SettingsComponent.Output.ThemeChanged -> {
                onOutput(HomeComponent.Output.ThemeChanged)
            }
        }
    }

    private sealed interface ChildConfig : Parcelable {

        @Parcelize
        object CheckPinCode : ChildConfig

        @Parcelize
        object UpcomingLaunchers : ChildConfig

        @Parcelize
        object Settings : ChildConfig

        @Parcelize
        object SolarSystem : ChildConfig
    }
}