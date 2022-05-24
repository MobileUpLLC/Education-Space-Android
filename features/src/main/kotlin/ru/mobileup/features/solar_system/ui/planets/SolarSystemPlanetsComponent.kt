package ru.mobileup.features.solar_system.ui.planets

import ru.mobileup.features.solar_system.ui.PlanetShortInfoViewData
import me.aartikov.sesame.loading.simple.Loading

interface SolarSystemPlanetsComponent {

    val planetsViewState: Loading.State<List<PlanetShortInfoViewData>>

    fun onRetryClick()
}