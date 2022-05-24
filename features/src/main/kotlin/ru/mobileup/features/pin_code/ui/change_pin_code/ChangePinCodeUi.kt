package ru.mobileup.features.pin_code.ui.change_pin_code

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.arkivanov.decompose.extensions.compose.jetpack.Children
import ru.mobileup.features.R
import ru.mobileup.core.theme.AppTheme
import ru.mobileup.core.utils.createFakeRouterState
import ru.mobileup.core.widget.Toolbar
import ru.mobileup.features.pin_code.ui.check_pin_code.CheckPinCodeUi
import ru.mobileup.features.pin_code.ui.check_pin_code.FakeCheckPinCodeComponent
import ru.mobileup.features.pin_code.ui.create_pin_code.CreatePinCodeUi

@Composable
fun ChangePinCodeUi(
    component: ChangePinCodeComponent,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = { Toolbar(title = stringResource(R.string.change_pincode_toolbar_title)) }
    ) { paddings ->
        Children(routerState = component.routerState, Modifier.padding(paddings)) { state ->
            when (val instance = state.instance) {
                is ChangePinCodeComponent.Child.Check -> CheckPinCodeUi(instance.component)
                is ChangePinCodeComponent.Child.Create -> CreatePinCodeUi(instance.component)
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun ChangePinCodeUiPreview() {
    AppTheme {
        ChangePinCodeUi(FakeChangePinCodeComponent())
    }
}

class FakeChangePinCodeComponent : ChangePinCodeComponent {
    override val routerState = createFakeRouterState(
        ChangePinCodeComponent.Child.Check(FakeCheckPinCodeComponent())
    )
}