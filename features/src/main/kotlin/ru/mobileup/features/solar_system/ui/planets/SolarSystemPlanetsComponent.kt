package ru.mobileup.features.solar_system.ui.planets

import me.aartikov.replica.single.Loadable
import ru.mobileup.features.solar_system.ui.PlanetShortInfoViewData

interface SolarSystemPlanetsComponent {

    val planetsViewState: Loadable<List<PlanetShortInfoViewData>>

    fun onRetryClick()
}