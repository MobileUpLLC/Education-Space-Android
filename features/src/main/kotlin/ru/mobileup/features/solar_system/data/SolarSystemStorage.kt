package ru.mobileup.features.solar_system.data

import ru.mobileup.features.solar_system.domain.PlanetShortInfo

interface SolarSystemStorage {

    suspend fun getPlanetsShortInfo(): List<PlanetShortInfo>
}