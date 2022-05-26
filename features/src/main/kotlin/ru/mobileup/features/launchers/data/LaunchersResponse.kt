package ru.mobileup.features.launchers.data

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.aartikov.sesame.localizedstring.LocalizedString
import ru.mobileup.features.launchers.domain.Launcher
import ru.mobileup.features.launchers.domain.LauncherId

@Serializable
class LauncherResponse(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("flight_number") val flightNumber: Int,
    @SerialName("date_utc") val launchDate: Instant,
    @SerialName("links") val links: LinksResponse?
)

@Serializable
class LinksResponse(
    @SerialName("patch") val patch: PatchResponse?
)

fun LauncherResponse.toDomain(): Launcher {
    return Launcher(
        id = LauncherId(id),
        name = LocalizedString.raw(name),
        flightNumber = flightNumber,
        launchDate = launchDate,
        patch = links?.patch?.toDomain()
    )
}