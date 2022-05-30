package ru.mobileup.features.pin_code.ui.check_pin_code

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
import ru.mobileup.features.R
import ru.mobileup.core.utils.resolve
import me.aartikov.sesame.dialog.DialogControl
import me.aartikov.sesame.localizedstring.LocalizedString
import ru.mobileup.core.widget.dialog.AlertDialogData
import ru.mobileup.core.widget.dialog.DialogResult
import ru.mobileup.core.widget.dialog.ShowAlertDialog

@Composable
fun CheckPinCodeUi(
    component: CheckPinCodeComponent,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
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
        ShowAlertDialog(dialogControl = component.forgottenPinCodeDialogControl)
    }
}

@Preview(showSystemUi = true)
@Composable
fun CheckPinCodeUiPreview() {
    AppTheme {
        CheckPinCodeUi(FakeCheckPinCodeComponent())
    }
}

class FakeCheckPinCodeComponent : CheckPinCodeComponent {

    override val title = LocalizedString.resource(R.string.check_pincode_confirm_title)

    override val forgottenPinCodeDialogControl = DialogControl<AlertDialogData, DialogResult>()

    override val pinCodeComponent: PinCodeComponent = FakePinCodeComponent()
}