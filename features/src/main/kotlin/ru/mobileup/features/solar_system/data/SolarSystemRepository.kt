package ru.mobileup.features.solar_system.data

import me.aartikov.replica.single.Replica
import ru.mobileup.features.solar_system.domain.PlanetShortInfo

interface SolarSystemRepository {

    val planetShortInfoReplica: Replica<List<PlanetShortInfo>>
}