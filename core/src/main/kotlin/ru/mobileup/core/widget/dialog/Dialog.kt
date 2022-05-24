package ru.mobileup.core.widget.dialog

import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import me.aartikov.sesame.dialog.DialogControl
import ru.mobileup.core.theme.AppTheme

@Composable
fun <T : Any, R : Any> ShowDialog(
    dialogControl: DialogControl<T, R>,
    dialog: @Composable (data: T) -> Unit
) {
    val state by dialogControl.stateFlow.collectAsState()
    (state as? DialogControl.State.Shown)?.data?.let { data ->
        dialog(data)
    }
}

@Composable
fun DialogTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.h6
    )
}

@Composable
fun DialogText(text: String) {
    Text(
        text = text,
        color = MaterialTheme.colors.onBackground,
        style = MaterialTheme.typography.body2
    )
}

@Composable
fun DialogButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TextButton(
        onClick = onClick,
        modifier = modifier,
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.body2
        )
    }
}

val <T : Any, R : Any> DialogControl<T, R>.dataOrNull: T?
    get() = (this.stateFlow.value as? DialogControl.State.Shown)?.data

@Preview
@Composable
fun AlertDialogPreview() {
    AppTheme {
        AlertDialog(
            title = { DialogTitle("Title") },
            text = { DialogText("Some message") },
            confirmButton = { DialogButton(text = "Ok", onClick = {}) },
            dismissButton = { DialogButton(text = "Cancel", onClick = {}) },
            onDismissRequest = {}
        )
    }
}