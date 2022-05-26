package ru.mobileup.features.solar_system

import com.arkivanov.decompose.ComponentContext
import ru.mobileup.core.ComponentFactory
import ru.mobileup.core.storage.BaseRoomDatabase
import ru.mobileup.features.solar_system.ui.planets.RealSolarSystemPlanetsComponent
import ru.mobileup.features.solar_system.ui.planets.SolarSystemPlanetsComponent
import org.koin.core.component.get
import org.koin.dsl.module
import ru.mobileup.features.solar_system.data.SolarSystemRepository
import ru.mobileup.features.solar_system.data.SolarSystemRepositoryImpl

val solarSystemModule = module {
    single { get<BaseRoomDatabase>().getPlanetsDao() }
    single<SolarSystemRepository> { SolarSystemRepositoryImpl(get(), get()) }
}

fun ComponentFactory.createSolarSystemPlanetsComponent(componentContext: ComponentContext): SolarSystemPlanetsComponent {
    val planetShortInfoReplica = get<SolarSystemRepository>().planetShortInfoReplica
    return RealSolarSystemPlanetsComponent(componentContext, get(), planetShortInfoReplica)
}