package ru.mobileup.features.solar_system.data

import ru.mobileup.core.storage.dto.PlanetsDao
import ru.mobileup.features.solar_system.domain.PlanetShortInfo
import ru.mobileup.features.solar_system.domain.toPlanetShortInfoDomain

class SolarSystemStorageImpl(private val planetsDao: PlanetsDao) : SolarSystemStorage {

    override suspend fun getPlanetsShortInfo(): List<PlanetShortInfo> {
        return planetsDao.getAllPlanets().map { it.toPlanetShortInfoDomain() }
    }
}