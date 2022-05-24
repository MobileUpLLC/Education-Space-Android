package ru.mobileup.features.solar_system.domain

import androidx.annotation.DrawableRes
import me.aartikov.sesame.localizedstring.LocalizedString
import ru.mobileup.core.R
import ru.mobileup.core.storage.dto.PlanetDb

data class PlanetShortInfo(
    val id: PlanetId,
    val name: LocalizedString,
    val description: LocalizedString,
    val type: Type
) {
    enum class Type(@DrawableRes val imageRes: Int?) {
        Sun(R.drawable.img_sun),
        Mercury(R.drawable.img_mercury),
        Venus(R.drawable.img_venus),
        Earth(R.drawable.img_earth),
        Mars(R.drawable.img_mars),
        Jupiter(R.drawable.img_jupiter),
        Saturn(R.drawable.img_saturn),
        Uranus(R.drawable.img_uranus),
        Neptune(R.drawable.img_neptune),
        Pluto(R.drawable.img_pluto),
        Unknown(null)
    }
}

@JvmInline
value class PlanetId(val value: String)

fun PlanetDb.toPlanetShortInfoDomain() = PlanetShortInfo(
    id = PlanetId(id),
    name = LocalizedString.raw(name),
    description = LocalizedString.raw(description),
    type = name.toType()
)

fun String.toType(): PlanetShortInfo.Type = when (this) {
    "Sun" -> PlanetShortInfo.Type.Sun
    "Mercury" -> PlanetShortInfo.Type.Mercury
    "Venus" -> PlanetShortInfo.Type.Venus
    "Earth" -> PlanetShortInfo.Type.Earth
    "Mars" -> PlanetShortInfo.Type.Mars
    "Jupiter" -> PlanetShortInfo.Type.Jupiter
    "Saturn" -> PlanetShortInfo.Type.Saturn
    "Uranus" -> PlanetShortInfo.Type.Uranus
    "Neptune" -> PlanetShortInfo.Type.Neptune
    "Pluto" -> PlanetShortInfo.Type.Pluto
    else -> PlanetShortInfo.Type.Unknown
}