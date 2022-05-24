package ru.mobileup.features.launchers

import com.arkivanov.decompose.ComponentContext
import ru.mobileup.core.ComponentFactory
import ru.mobileup.core.network.NetworkApiFactory
import ru.mobileup.features.launchers.data.LauncherApi
import ru.mobileup.features.launchers.data.LauncherGateway
import ru.mobileup.features.launchers.data.LauncherGatewayImpl
import ru.mobileup.features.launchers.domain.LoadUpcomingLaunchersInteractor
import ru.mobileup.features.launchers.ui.upcoming.UpcomingLaunchersComponent
import ru.mobileup.features.launchers.ui.upcoming.RealUpcomingLaunchersComponent
import org.koin.dsl.module
import org.koin.core.component.get

val launchersModule = module {
    single { createLauncherApi(get()) }
    single<LauncherGateway> { LauncherGatewayImpl(get()) }
    factory { LoadUpcomingLaunchersInteractor(get()) }
}

private fun createLauncherApi(factory: NetworkApiFactory): LauncherApi {
    return factory.createUnauthorizedApi()
}

fun ComponentFactory.createUpcomingLaunchersComponent(componentContext: ComponentContext): UpcomingLaunchersComponent {
    return RealUpcomingLaunchersComponent(componentContext, get(), get())
}