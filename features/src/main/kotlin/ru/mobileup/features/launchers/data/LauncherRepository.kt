package ru.mobileup.features.launchers.data

import me.aartikov.replica.single.Replica
import ru.mobileup.features.launchers.domain.Launcher

interface LauncherRepository {

    val upcomingLaunchersReplica: Replica<List<Launcher>>
}