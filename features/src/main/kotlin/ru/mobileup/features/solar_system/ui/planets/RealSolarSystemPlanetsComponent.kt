package ru.mobileup.features.solar_system.ui.planets

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.ComponentContext
import me.aartikov.replica.single.Replica
import me.aartikov.replica.single.mapData
import ru.mobileup.core.error_handling.ErrorHandler
import ru.mobileup.core.utils.observe
import ru.mobileup.features.solar_system.domain.PlanetShortInfo
import ru.mobileup.features.solar_system.ui.toViewData

class RealSolarSystemPlanetsComponent(
    componentContext: ComponentContext,
    errorHandler: ErrorHandler,
    private val planetsReplica: Replica<List<PlanetShortInfo>>
) : ComponentContext by componentContext, SolarSystemPlanetsComponent {

    private val planetsReplicaState by planetsReplica.observe(lifecycle, errorHandler)

    override val planetsViewState by derivedStateOf {
        planetsReplicaState.mapData { planets ->
            planets.map { it.toViewData() }
        }
    }

    override fun onRetryClick() {
        planetsReplica.refresh()
    }
}