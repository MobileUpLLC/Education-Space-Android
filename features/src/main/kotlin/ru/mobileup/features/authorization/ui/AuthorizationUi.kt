package ru.mobileup.features.authorization.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.arkivanov.decompose.extensions.compose.jetpack.Children
import ru.mobileup.core.theme.AppTheme
import ru.mobileup.core.utils.createFakeRouterState
import ru.mobileup.features.pin_code.ui.create_pin_code.CreatePinCodeUi
import ru.mobileup.features.pin_code.ui.create_pin_code.FakeCreatePinCodeComponent

@Composable
fun AuthorizationUi(
    component: AuthorizationComponent,
    modifier: Modifier = Modifier
) {
    Children(component.routerState, modifier) { child ->
        when (val instance = child.instance) {
            is AuthorizationComponent.Child.CreateNewPinCode -> CreatePinCodeUi(instance.component)
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun AuthorizationUiPreview() {
    AppTheme {
        AuthorizationUi(FakeAuthorizationComponent())
    }
}

class FakeAuthorizationComponent : AuthorizationComponent {
    override val routerState = createFakeRouterState(
        AuthorizationComponent.Child.CreateNewPinCode(FakeCreatePinCodeComponent())
    )
}