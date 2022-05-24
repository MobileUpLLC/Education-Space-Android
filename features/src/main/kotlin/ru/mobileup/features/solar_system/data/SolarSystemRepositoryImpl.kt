package ru.mobileup.features.solar_system.data

import me.aartikov.replica.client.ReplicaClient
import me.aartikov.replica.single.Replica
import me.aartikov.replica.single.ReplicaSettings
import ru.mobileup.core.storage.dto.PlanetsDao
import ru.mobileup.features.solar_system.domain.PlanetShortInfo
import ru.mobileup.features.solar_system.domain.toPlanetShortInfoDomain
import kotlin.time.Duration.Companion.hours

class SolarSystemRepositoryImpl(
    replicaClient: ReplicaClient,
    planetsDao: PlanetsDao
) : SolarSystemRepository {

    override val planetShortInfoReplica: Replica<List<PlanetShortInfo>> =
        replicaClient.createReplica(
            name = "planetShortInfo",
            settings = ReplicaSettings(
                staleTime = 1.hours,
                clearTime = 2.hours
            )
        ) {
            planetsDao.getAllPlanets().map { it.toPlanetShortInfoDomain() }
        }
}