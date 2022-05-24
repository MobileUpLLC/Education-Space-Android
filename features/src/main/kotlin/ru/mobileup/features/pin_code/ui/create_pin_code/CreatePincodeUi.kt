package ru.mobileup.features.pin_code.ui.create_pin_code

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.mobileup.core.theme.AppTheme
import ru.mobileup.features.pin_code.ui.pin_code.FakePinCodeComponent
import ru.mobileup.features.pin_code.ui.pin_code.PinCodeComponent
import ru.mobileup.features.pin_code.ui.pin_code.PinCodeUi
import me.aartikov.sesame.localizedstring.LocalizedString
import ru.mobileup.features.R
import ru.mobileup.core.utils.resolve

@Composable
fun CreatePinCodeUi(
    component: CreatePinCodeComponent,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, top = 100.dp),
                text = component.title.resolve(),
                style = MaterialTheme.typography.body2,
                textAlign = TextAlign.Center
            )
            PinCodeUi(
                component = component.pinCodeComponent,
                modifier = Modifier.padding(bottom = 32.dp)
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun CreatePinCodeUiPreview() {
    AppTheme {
        CreatePinCodeUi(FakeCreatePinCodeComponent())
    }
}

class FakeCreatePinCodeComponent : CreatePinCodeComponent {

    override val title = LocalizedString.resource(R.string.pincode_create_setup_title)

    override val pinCodeComponent: PinCodeComponent = FakePinCodeComponent()
}