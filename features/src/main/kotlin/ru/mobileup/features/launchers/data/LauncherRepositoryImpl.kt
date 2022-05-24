package ru.mobileup.features.launchers.data

import me.aartikov.replica.client.ReplicaClient
import me.aartikov.replica.single.Replica
import me.aartikov.replica.single.ReplicaSettings
import ru.mobileup.features.launchers.domain.Launcher
import kotlin.time.Duration.Companion.hours

class LauncherRepositoryImpl(replicaClient: ReplicaClient, api: LauncherApi) : LauncherRepository {

    override val upcomingLaunchersReplica: Replica<List<Launcher>> =
        replicaClient.createReplica(
            name = "upcomingLaunchers",
            settings = ReplicaSettings(
                staleTime = 1.hours,
                clearTime = 2.hours
            )
        ) {
            api.getUpcomingLaunchers().map { it.toDomain() }
        }
}