package ru.mobileup.features.solar_system.domain

import ru.mobileup.features.solar_system.data.SolarSystemStorage

class GetPlanetsShortInfoInteractor(private val solarSystemStorage: SolarSystemStorage) {

    suspend fun execute(): List<PlanetShortInfo> {
        return solarSystemStorage.getPlanetsShortInfo()
    }
}