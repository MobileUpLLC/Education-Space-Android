package ru.mobileup.education_space.settings

import me.aartikov.sesame.localizedstring.LocalizedString
import ru.mobileup.core.R
import ru.mobileup.core.widget.dialog.AlertDialogData

object FakeSettings {

    val logoutAlertDialog = AlertDialogData(
        message = LocalizedString.resource(ru.mobileup.features.R.string.settings_logout_description),
        positiveButtonText = LocalizedString.resource(ru.mobileup.features.R.string.settings_logout_button),
        dismissButtonText = LocalizedString.resource(R.string.common_cancel)
    )
}