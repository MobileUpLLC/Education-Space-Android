package ru.mobileup.features.solar_system

import com.arkivanov.decompose.ComponentContext
import ru.mobileup.core.ComponentFactory
import ru.mobileup.core.storage.BaseRoomDatabase
import ru.mobileup.features.solar_system.data.SolarSystemStorage
import ru.mobileup.features.solar_system.data.SolarSystemStorageImpl
import ru.mobileup.features.solar_system.domain.GetPlanetsShortInfoInteractor
import ru.mobileup.features.solar_system.ui.planets.RealSolarSystemPlanetsComponent
import ru.mobileup.features.solar_system.ui.planets.SolarSystemPlanetsComponent
import org.koin.core.component.get
import org.koin.dsl.module

val solarSystemModule = module {
    single { get<BaseRoomDatabase>().getPlanetsDao() }
    single<SolarSystemStorage> { SolarSystemStorageImpl(get()) }
    factory { GetPlanetsShortInfoInteractor(get()) }
}

fun ComponentFactory.createSolarSystemPlanetsComponent(componentContext: ComponentContext): SolarSystemPlanetsComponent {
    return RealSolarSystemPlanetsComponent(componentContext, get(), get())
}