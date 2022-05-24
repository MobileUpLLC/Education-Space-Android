package ru.mobileup.features.launchers.domain

import ru.mobileup.features.launchers.data.LauncherGateway

class LoadUpcomingLaunchersInteractor(private val launcherGateway: LauncherGateway) {

    suspend fun execute(): List<Launcher> {
        return launcherGateway.getUpcomingLaunchers()
    }
}