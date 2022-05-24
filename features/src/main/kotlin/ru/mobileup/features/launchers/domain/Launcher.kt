package ru.mobileup.features.launchers.domain

import kotlinx.datetime.Instant
import me.aartikov.sesame.localizedstring.LocalizedString

data class Launcher(
    val id: LauncherId,
    val launchDate: Instant,
    val name: LocalizedString,
    val flightNumber: Int,
    val patch: Patch?
)

@JvmInline
value class LauncherId(val value: String)