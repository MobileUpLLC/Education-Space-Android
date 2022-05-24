package ru.mobileup.features.settings.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnStart
import ru.mobileup.features.R
import ru.mobileup.core.error_handling.ErrorHandler
import ru.mobileup.core.utils.componentCoroutineScope
import ru.mobileup.core.error_handling.safeLaunch
import ru.mobileup.features.app_theme.domain.ChangeDarkThemeEnabledInteractor
import ru.mobileup.features.app_theme.domain.IsDarkThemeEnabledInteractor
import ru.mobileup.core.widget.dialog.AlertDialogData
import ru.mobileup.core.widget.dialog.DialogResult
import ru.mobileup.features.settings.domain.LogoutInteractor
import me.aartikov.sesame.dialog.DialogControl
import me.aartikov.sesame.localizedstring.LocalizedString

class RealSettingsComponent(
    componentContext: ComponentContext,
    private val onOutput: (SettingsComponent.Output) -> Unit,
    private val errorHandler: ErrorHandler,
    isDarkThemeEnabledInteractor: IsDarkThemeEnabledInteractor,
    private val changeDarkThemeEnabledInteractor: ChangeDarkThemeEnabledInteractor,
    private val logoutInteractor: LogoutInteractor
) : ComponentContext by componentContext, SettingsComponent {

    private val coroutineScope = componentCoroutineScope()

    override var darkThemeEnabled: Boolean by mutableStateOf(false)

    override val logoutDialogControl = DialogControl<AlertDialogData, DialogResult>()

    init {
        lifecycle.doOnStart {
            coroutineScope.safeLaunch(errorHandler) {
                darkThemeEnabled = isDarkThemeEnabledInteractor.execute()
            }
        }
    }

    override fun onExitClick() {
        coroutineScope.safeLaunch(errorHandler) {
            val data = AlertDialogData(
                message = LocalizedString.resource(R.string.settings_logout_description),
                positiveButtonText = LocalizedString.resource(R.string.settings_logout_button),
                dismissButtonText = LocalizedString.resource(ru.mobileup.core.R.string.common_cancel)
            )
            val result = logoutDialogControl.showForResult(data) ?: DialogResult.Cancel
            if (result == DialogResult.Confirm) {
                logoutInteractor.execute()
                onOutput(SettingsComponent.Output.LoggedOut)
            }
        }
    }

    override fun onPinCodeClick() {
        onOutput(SettingsComponent.Output.PinCodeChangingRequested)
    }

    override fun onDarkThemeSettingsChecked() {
        coroutineScope.safeLaunch(errorHandler) {
            val newDarkThemeEnabled = !darkThemeEnabled
            changeDarkThemeEnabledInteractor.execute(newDarkThemeEnabled)
            darkThemeEnabled = newDarkThemeEnabled
            onOutput(SettingsComponent.Output.ThemeChanged)
        }
    }
}