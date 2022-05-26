package ru.mobileup.core

import me.aartikov.replica.client.ReplicaClient
import me.aartikov.replica.devtools.ReplicaDevTools
import me.aartikov.replica.network.AndroidNetworkConnectivityProvider
import me.aartikov.replica.network.NetworkConnectivityProvider
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.mobileup.core.activity.ActivityProvider
import ru.mobileup.core.biometric.BiometricService
import ru.mobileup.core.biometric.BiometricServiceImpl
import ru.mobileup.core.debug_tools.DebugTools
import ru.mobileup.core.error_handling.ErrorHandler
import ru.mobileup.core.exit.ExitService
import ru.mobileup.core.exit.ExitServiceImpl
import ru.mobileup.core.message.data.MessageService
import ru.mobileup.core.message.data.MessageServiceImpl
import ru.mobileup.core.network.BaseUrlProvider
import ru.mobileup.core.network.NetworkApiFactory
import ru.mobileup.core.network.RealBaseUrlProvider
import ru.mobileup.core.storage.RealRoomDatabaseFactory
import ru.mobileup.core.storage.RealSharedPreferencesFactory
import ru.mobileup.core.storage.SharedPreferencesFactory
import ru.mobileup.core.time.TimeGateway
import ru.mobileup.core.time.TimeGatewayImpl

fun coreModule(backendUrl: String) = module {
    single { ActivityProvider() }
    single<SharedPreferencesFactory> { RealSharedPreferencesFactory() }
    single<ExitService> { ExitServiceImpl(get()) }
    single<BiometricService> { BiometricServiceImpl(get(), androidContext()) }
    single<BaseUrlProvider> { RealBaseUrlProvider(backendUrl) }
    single<NetworkConnectivityProvider> { AndroidNetworkConnectivityProvider(androidApplication()) }
    single { ReplicaClient(get()) }
    single { ReplicaDevTools(get(), androidContext()) }
    single<MessageService> { MessageServiceImpl() }
    single { ErrorHandler(get()) }
    single { RealRoomDatabaseFactory().createDatabaseInstance(androidContext()) }
    single<DebugTools> { RealDebugToolsImpl(androidContext()) }
    single { NetworkApiFactory(get(), get()) }
    single<TimeGateway> { TimeGatewayImpl() }
}