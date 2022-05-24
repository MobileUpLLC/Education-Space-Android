package ru.mobileup.features.launchers.ui.upcoming

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnCreate
import com.arkivanov.essenty.lifecycle.doOnStart
import ru.mobileup.core.error_handling.ErrorHandler
import ru.mobileup.core.utils.componentCoroutineScope
import ru.mobileup.core.utils.toComposeState
import ru.mobileup.features.launchers.domain.LoadUpcomingLaunchersInteractor
import ru.mobileup.features.launchers.ui.toViewData
import me.aartikov.sesame.loading.simple.OrdinaryLoading
import me.aartikov.sesame.loading.simple.mapData
import me.aartikov.sesame.loading.simple.refresh
import ru.mobileup.core.utils.handleErrors

class RealUpcomingLaunchersComponent(
    componentContext: ComponentContext,
    private val errorHandler: ErrorHandler,
    loadUpcomingLaunchersInteractor: LoadUpcomingLaunchersInteractor,
) : ComponentContext by componentContext, UpcomingLaunchersComponent {

    private val coroutineScope = componentCoroutineScope()

    private val upcomingLaunchersLoading = OrdinaryLoading(
        scope = coroutineScope,
        load = { loadUpcomingLaunchersInteractor.execute() }
    )

    private val upcomingLaunchersState by upcomingLaunchersLoading.stateFlow.toComposeState(coroutineScope)

    override val upcomingLaunchersViewState by derivedStateOf {
        upcomingLaunchersState.mapData { launchers ->
            launchers.map { it.toViewData() }
        }
    }

    init {
        lifecycle.doOnCreate {
            upcomingLaunchersLoading.handleErrors(coroutineScope, errorHandler)
        }
        lifecycle.doOnStart {
            upcomingLaunchersLoading.refresh()
        }
    }

    override fun onRetryClick() {
        upcomingLaunchersLoading.refresh()
    }
}