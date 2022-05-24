package ru.mobileup.features.launchers.ui.upcoming

import ru.mobileup.features.launchers.ui.LauncherViewData
import me.aartikov.sesame.loading.simple.Loading

interface UpcomingLaunchersComponent {

    val upcomingLaunchersViewState: Loading.State<List<LauncherViewData>>

    fun onRetryClick()
}