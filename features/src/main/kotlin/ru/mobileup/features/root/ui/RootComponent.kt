package ru.mobileup.features.root.ui

import com.arkivanov.decompose.router.RouterState
import ru.mobileup.features.app_theme.ui.AppThemeComponent
import ru.mobileup.features.authorization.ui.AuthorizationComponent
import ru.mobileup.features.home.ui.HomeComponent
import ru.mobileup.core.message.ui.MessageComponent
import ru.mobileup.features.pin_code.ui.change_pin_code.ChangePinCodeComponent
import ru.mobileup.features.start.ui.StartComponent

interface RootComponent {

    val appThemeComponent: AppThemeComponent
    val messageComponent: MessageComponent
    val routerState: RouterState<*, Child>

    sealed interface Child {

        class Start(val component: StartComponent) : Child

        class Authorization(val component: AuthorizationComponent) : Child

        class Home(val component: HomeComponent) : Child

        class ChangePinCode(val component: ChangePinCodeComponent) : Child
    }
}