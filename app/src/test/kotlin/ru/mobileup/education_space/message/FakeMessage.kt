package ru.mobileup.education_space.message

import me.aartikov.sesame.localizedstring.LocalizedString
import ru.mobileup.core.message.domain.MessageData

object FakeMessage {

    val messageWithIcon = MessageData(
        text = LocalizedString.raw("abcd"),
        iconRes = 1234567890
    )

    val messageWithoutIcon = MessageData(LocalizedString.raw("abcde"))
}