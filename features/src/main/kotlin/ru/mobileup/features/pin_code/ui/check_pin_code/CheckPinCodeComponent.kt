package ru.mobileup.features.pin_code.ui.check_pin_code

import ru.mobileup.features.pin_code.ui.pin_code.PinCodeComponent
import me.aartikov.sesame.dialog.DialogControl
import me.aartikov.sesame.localizedstring.LocalizedString
import ru.mobileup.core.widget.dialog.AlertDialogData
import ru.mobileup.core.widget.dialog.DialogResult

interface CheckPinCodeComponent {

    val title: LocalizedString

    val forgottenPinCodeDialogControl: DialogControl<AlertDialogData, DialogResult>

    val pinCodeComponent: PinCodeComponent

    sealed interface Output {

        object PinCodeChecked : Output

        object LoggedOut : Output
    }
}