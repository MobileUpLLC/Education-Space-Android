package ru.mobileup.features.home.ui

import android.os.Parcelable
import com.arkivanov.decompose.router.RouterState
import ru.mobileup.features.launchers.ui.upcoming.UpcomingLaunchersComponent
import ru.mobileup.features.pin_code.ui.check_pin_code.CheckPinCodeComponent
import ru.mobileup.features.settings.ui.SettingsComponent
import ru.mobileup.features.solar_system.ui.planets.SolarSystemPlanetsComponent
import kotlinx.parcelize.Parcelize

interface HomeComponent {

    val routerState: RouterState<*, Child>
    val isBottomBarVisible: Boolean

    enum class Page {
        UpcomingLaunchers, SolarSystem, Settings
    }

    sealed interface Output {
        object LoggedOut : Output
        object PinCodeChangingRequested : Output
        object ThemeChanged : Output
    }

    sealed interface Child {
        class CheckPinCode(val component: CheckPinCodeComponent) : Child
        class UpcomingLaunchers(val component: UpcomingLaunchersComponent) : Child
        class Settings(val component: SettingsComponent) : Child
        class SolarSystem(val component: SolarSystemPlanetsComponent) : Child
    }

    sealed interface InitialScreen : Parcelable {
        @Parcelize
        object CheckPinCode : InitialScreen

        @Parcelize
        object UpcomingLaunchers : InitialScreen
    }

    fun onPageSelected(page: Page)
}