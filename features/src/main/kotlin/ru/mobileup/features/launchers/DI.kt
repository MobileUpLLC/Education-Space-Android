package ru.mobileup.features.launchers

import com.arkivanov.decompose.ComponentContext
import ru.mobileup.features.launchers.data.LauncherApi
import ru.mobileup.core.ComponentFactory
import ru.mobileup.core.network.NetworkApiFactory
import ru.mobileup.features.launchers.ui.upcoming.UpcomingLaunchersComponent
import ru.mobileup.features.launchers.ui.upcoming.RealUpcomingLaunchersComponent
import org.koin.dsl.module
import org.koin.core.component.get
import ru.mobileup.features.launchers.data.LauncherRepository
import ru.mobileup.features.launchers.data.LauncherRepositoryImpl

val launchersModule = module {
    single { createLauncherApi(get()) }
    single<LauncherRepository> { LauncherRepositoryImpl(get(), get()) }
}

private fun createLauncherApi(factory: NetworkApiFactory): LauncherApi {
    return factory.createUnauthorizedApi()
}

fun ComponentFactory.createUpcomingLaunchersComponent(componentContext: ComponentContext): UpcomingLaunchersComponent {
    val upcomingLaunchersReplica = get<LauncherRepository>().upcomingLaunchersReplica
    return RealUpcomingLaunchersComponent(componentContext, get(), upcomingLaunchersReplica)
}