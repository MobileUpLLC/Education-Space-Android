package ru.mobileup.features.root.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetpack.Children
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import ru.mobileup.features.app_theme.ui.AppThemeUi
import ru.mobileup.features.app_theme.ui.FakeAppThemeComponent
import ru.mobileup.features.authorization.ui.AuthorizationUi
import ru.mobileup.features.authorization.ui.FakeAuthorizationComponent
import ru.mobileup.core.theme.AppTheme
import ru.mobileup.core.theme.LocalThemeType
import ru.mobileup.core.theme.ThemeType
import ru.mobileup.core.utils.createFakeRouterState
import ru.mobileup.features.home.ui.HomeUi
import ru.mobileup.core.message.ui.FakeMessageComponent
import ru.mobileup.core.message.ui.MessageUi
import ru.mobileup.features.pin_code.ui.change_pin_code.ChangePinCodeUi
import ru.mobileup.features.start.ui.StartUi

@Composable
fun RootUi(
    component: RootComponent,
    modifier: Modifier = Modifier
) {
    AppThemeUi(component.appThemeComponent) {
        val systemUiController = rememberSystemUiController()
        val surfaceColor = MaterialTheme.colors.surface
        val statusBarDarkContentEnabled = LocalThemeType.current != ThemeType.DarkTheme
        LaunchedEffect(surfaceColor, statusBarDarkContentEnabled) {
            systemUiController.setStatusBarColor(surfaceColor)
            systemUiController.statusBarDarkContentEnabled = statusBarDarkContentEnabled
            systemUiController.setNavigationBarColor(surfaceColor)
        }

        Surface(
            modifier = modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            Box {
                Children(component.routerState) { child ->
                    when (val instance = child.instance) {
                        is RootComponent.Child.Start -> StartUi()
                        is RootComponent.Child.Authorization -> AuthorizationUi(instance.component)
                        is RootComponent.Child.Home -> HomeUi(instance.component)
                        is RootComponent.Child.ChangePinCode -> ChangePinCodeUi(instance.component)
                    }
                }

                MessageUi(component = component.messageComponent, bottomPadding = 16.dp)
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun RootUiPreview() {
    AppTheme {
        RootUi(FakeRootComponent())
    }
}

class FakeRootComponent : RootComponent {
    override val appThemeComponent = FakeAppThemeComponent()
    override val messageComponent = FakeMessageComponent()
    override val routerState = createFakeRouterState(
        RootComponent.Child.Authorization(FakeAuthorizationComponent())
    )
}