package ru.mobileup.features.solar_system.ui.planets

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnCreate
import ru.mobileup.core.error_handling.ErrorHandler
import ru.mobileup.core.utils.componentCoroutineScope
import ru.mobileup.core.utils.handleErrors
import ru.mobileup.core.utils.toComposeState
import ru.mobileup.features.solar_system.domain.GetPlanetsShortInfoInteractor
import ru.mobileup.features.solar_system.ui.toViewData
import me.aartikov.sesame.loading.simple.OrdinaryLoading
import me.aartikov.sesame.loading.simple.mapData
import me.aartikov.sesame.loading.simple.refresh

class RealSolarSystemPlanetsComponent(
    componentContext: ComponentContext,
    private val errorHandler: ErrorHandler,
    private val getPlanetsShortInfoInteractor: GetPlanetsShortInfoInteractor
) : ComponentContext by componentContext, SolarSystemPlanetsComponent {

    private val coroutineScope = componentCoroutineScope()

    private val planetsLoading = OrdinaryLoading(
        scope = coroutineScope,
        load = { getPlanetsShortInfoInteractor.execute() }
    )

    private val planetsState by planetsLoading.stateFlow.toComposeState(coroutineScope)

    override val planetsViewState by derivedStateOf {
        planetsState.mapData { planets ->
            planets.map { it.toViewData() }
        }
    }

    init {
        lifecycle.doOnCreate {
            planetsLoading.handleErrors(coroutineScope, errorHandler)
            planetsLoading.refresh()
        }
    }

    override fun onRetryClick() {
        planetsLoading.refresh()
    }
}