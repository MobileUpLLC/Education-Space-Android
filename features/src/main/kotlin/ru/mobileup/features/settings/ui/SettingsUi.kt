package ru.mobileup.features.settings.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.mobileup.core.theme.AppTheme
import ru.mobileup.core.widget.Toolbar
import ru.mobileup.features.R
import ru.mobileup.core.widget.MenuItem
import ru.mobileup.core.widget.dialog.AlertDialogData
import ru.mobileup.core.widget.dialog.DialogResult
import ru.mobileup.core.widget.dialog.ShowAlertDialog
import me.aartikov.sesame.dialog.DialogControl

@Composable
fun SettingsUi(
    component: SettingsComponent,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            Toolbar(
                title = stringResource(id = R.string.settings_title),
                navigationIcon = null
            )
        },
        content = {
            Column {
                MenuItem(
                    painter = painterResource(id = ru.mobileup.core.R.drawable.ic_24_dark_mode),
                    text = stringResource(id = R.string.settings_dark_theme),
                    contentPadding = PaddingValues(
                        start = 16.dp,
                        top = 8.dp,
                        end = 8.dp,
                        bottom = 8.dp,
                    ),
                    actionElement = {
                        Checkbox(
                            modifier = modifier,
                            checked = component.darkThemeEnabled,
                            onCheckedChange = { component.onDarkThemeSettingsChecked() },
                            colors = CheckboxDefaults.colors(
                                checkmarkColor = MaterialTheme.colors.background,
                                uncheckedColor = MaterialTheme.colors.onSurface
                            )
                        )
                    }
                )

                MenuItem(
                    painter = painterResource(id = ru.mobileup.core.R.drawable.ic_24_pin),
                    modifier = modifier.clickable(onClick = component::onPinCodeClick),
                    text = stringResource(R.string.settings_change_pin_code)
                )

                MenuItem(
                    painter = painterResource(id = ru.mobileup.core.R.drawable.ic_24_exit_app),
                    modifier = modifier.clickable(onClick = component::onExitClick),
                    text = stringResource(R.string.settings_exit),
                    actionElement = null
                )
            }

            ShowAlertDialog(dialogControl = component.logoutDialogControl)
        }
    )
}

@Preview(showSystemUi = true)
@Composable
fun SettingsUiPreview() {
    AppTheme {
        SettingsUi(FakeSettingsComponent())
    }
}

class FakeSettingsComponent : SettingsComponent {

    override val darkThemeEnabled: Boolean = false
    override val logoutDialogControl = DialogControl<AlertDialogData, DialogResult>()

    override fun onExitClick() = Unit
    override fun onPinCodeClick() = Unit
    override fun onDarkThemeSettingsChecked() = Unit
}