package ru.mobileup.core.widget.dialog

import androidx.compose.material.AlertDialog
import androidx.compose.runtime.Composable
import me.aartikov.sesame.dialog.DialogControl
import me.aartikov.sesame.localizedstring.LocalizedString
import ru.mobileup.core.utils.resolve

@Composable
fun ShowAlertDialog(dialogControl: DialogControl<AlertDialogData, DialogResult>) {
    ShowDialog(dialogControl) { data ->
        AlertDialogUi(data, dialogControl)
    }
}

@Composable
fun AlertDialogUi(
    data: AlertDialogData,
    dialogControl: DialogControl<AlertDialogData, DialogResult>
) {
    AlertDialog(
        title = data.title?.let {
            { DialogTitle(it.resolve()) }
        },
        text = data.message?.let {
            { DialogText(text = it.resolve()) }
        },
        confirmButton = {
            DialogButton(
                text = data.positiveButtonText.resolve(),
                onClick = { dialogControl.sendResult(DialogResult.Confirm) }
            )
        },
        dismissButton = {
            data.dismissButtonText?.let { text ->
                DialogButton(
                    text = text.resolve(),
                    onClick = { dialogControl.sendResult(DialogResult.Cancel) }
                )
            }
        },
        onDismissRequest = {
            if (data.isCancelable) dialogControl.dismiss()
        }
    )
}

data class AlertDialogData(
    val title: LocalizedString? = null,
    val message: LocalizedString? = null,
    val positiveButtonText: LocalizedString = LocalizedString.resource(android.R.string.ok),
    val dismissButtonText: LocalizedString? = null,
    val isCancelable: Boolean = true
)