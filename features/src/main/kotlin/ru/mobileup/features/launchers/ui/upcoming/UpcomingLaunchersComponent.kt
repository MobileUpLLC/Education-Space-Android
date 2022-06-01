package ru.mobileup.features.launchers.ui.upcoming

import me.aartikov.replica.single.Loadable
import ru.mobileup.features.launchers.ui.LauncherViewData

interface UpcomingLaunchersComponent {

    val upcomingLaunchersViewState: Loadable<List<LauncherViewData>>

    fun onRetryClick()
}