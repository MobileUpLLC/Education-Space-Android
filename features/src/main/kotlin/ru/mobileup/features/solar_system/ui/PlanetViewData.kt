package ru.mobileup.features.solar_system.ui

import androidx.annotation.DrawableRes
import ru.mobileup.core.R
import ru.mobileup.features.solar_system.domain.PlanetId
import ru.mobileup.features.solar_system.domain.PlanetShortInfo
import me.aartikov.sesame.localizedstring.LocalizedString
import kotlin.random.Random

data class PlanetShortInfoViewData(
    val id: PlanetId,
    val name: LocalizedString,
    val description: LocalizedString,
    @DrawableRes val imageRes: Int?
) {
    companion object {
        val MOCK = PlanetShortInfoViewData(
            id = PlanetId("1"),
            name = LocalizedString.raw("Mars"),
            description = LocalizedString.raw("Mars"),
            imageRes = R.drawable.img_mars
        )

        fun mocks(size: Int = 10): List<PlanetShortInfoViewData> {
            return List(size) {
                MOCK.copy(id = PlanetId(Random.nextInt().toString()))
            }
        }
    }
}

fun PlanetShortInfo.toViewData() = PlanetShortInfoViewData(
    id = id,
    name = name,
    description = description,
    imageRes = type.imageRes
)