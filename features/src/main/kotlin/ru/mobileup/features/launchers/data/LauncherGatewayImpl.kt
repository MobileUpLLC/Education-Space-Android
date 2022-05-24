package ru.mobileup.features.launchers.data

import ru.mobileup.features.launchers.domain.Launcher

class LauncherGatewayImpl(private val api: LauncherApi) : LauncherGateway {

    override suspend fun getUpcomingLaunchers(): List<Launcher> {
        return api.getUpcomingLaunchers().map { it.toDomain() }
    }
}