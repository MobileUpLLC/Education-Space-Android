package ru.mobileup.features.launchers.ui

import ru.mobileup.features.R
import ru.mobileup.core.utils.DateTimeLocalizedString
import ru.mobileup.features.launchers.domain.Launcher
import ru.mobileup.features.launchers.domain.LauncherId
import kotlinx.datetime.Clock
import me.aartikov.sesame.localizedstring.LocalizedString
import kotlin.random.Random

data class LauncherViewData(
    val id: LauncherId,
    val launchDate: LocalizedString,
    val name: LocalizedString,
    val flightNumber: LocalizedString,
    val patchImage: String
) {
    companion object {
        val MOCK = LauncherViewData(
            id = LauncherId("1"),
            name = LocalizedString.raw("Starlink 4-8 (v1.5)"),
            launchDate = DateTimeLocalizedString(Clock.System.now()),
            flightNumber = LocalizedString.raw("# 1"),
            patchImage = "https://example.com"
        )

        fun mocks(size: Int = 5): List<LauncherViewData> {
            return List(size) {
                val number = Random.nextInt()
                MOCK.copy(
                    id = LauncherId(number.toString()),
                    flightNumber = LocalizedString.resource(
                        R.string.launchers_flight_number,
                        number
                    ),
                )
            }
        }
    }
}

fun Launcher.toViewData(): LauncherViewData {
    return LauncherViewData(
        id = id,
        launchDate = DateTimeLocalizedString(launchDate),
        name = name,
        flightNumber = LocalizedString.resource(R.string.launchers_flight_number, flightNumber),
        patchImage = patch?.small ?: ""
    )
}