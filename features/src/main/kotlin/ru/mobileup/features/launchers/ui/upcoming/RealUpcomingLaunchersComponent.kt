package ru.mobileup.features.launchers.ui.upcoming

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.ComponentContext
import me.aartikov.replica.single.Replica
import ru.mobileup.core.error_handling.ErrorHandler
import ru.mobileup.features.launchers.domain.Launcher
import ru.mobileup.features.launchers.ui.toViewData
import  ru.mobileup.core.utils.observe
import me.aartikov.replica.single.mapData

class RealUpcomingLaunchersComponent(
    componentContext: ComponentContext,
    errorHandler: ErrorHandler,
    private val upcomingLaunchersReplica: Replica<List<Launcher>>
) : ComponentContext by componentContext, UpcomingLaunchersComponent {

    private val upcomingLaunchersState by upcomingLaunchersReplica.observe(lifecycle, errorHandler)

    override val upcomingLaunchersViewState by derivedStateOf {
        upcomingLaunchersState.mapData { launchers ->
            launchers.map { it.toViewData() }
        }
    }

    override fun onRetryClick() {
        upcomingLaunchersReplica.refresh()
    }
}