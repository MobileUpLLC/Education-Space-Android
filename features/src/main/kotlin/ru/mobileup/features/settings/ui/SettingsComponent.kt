package ru.mobileup.features.settings.ui

import ru.mobileup.core.widget.dialog.AlertDialogData
import ru.mobileup.core.widget.dialog.DialogResult
import me.aartikov.sesame.dialog.DialogControl

interface SettingsComponent {

    sealed interface Output {
        object LoggedOut : Output
        object PinCodeChangingRequested : Output
        object ThemeChanged : Output
    }

    val darkThemeEnabled: Boolean
    val logoutDialogControl: DialogControl<AlertDialogData, DialogResult>

    fun onExitClick()
    fun onPinCodeClick()
    fun onDarkThemeSettingsChecked()
}