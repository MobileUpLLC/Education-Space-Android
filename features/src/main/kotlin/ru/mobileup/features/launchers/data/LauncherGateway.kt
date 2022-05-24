package ru.mobileup.features.launchers.data

import ru.mobileup.features.launchers.domain.Launcher

interface LauncherGateway {

    suspend fun getUpcomingLaunchers(): List<Launcher>
}