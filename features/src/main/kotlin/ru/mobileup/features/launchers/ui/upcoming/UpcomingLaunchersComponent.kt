package ru.mobileup.features.launchers.ui.upcoming

import me.aartikov.replica.single.Loadable
import ru.mobileup.features.launchers.ui.LauncherViewData
import me.aartikov.sesame.loading.simple.Loading

interface UpcomingLaunchersComponent {

    val upcomingLaunchersViewState: Loadable<List<LauncherViewData>>

    fun onRetryClick()
}