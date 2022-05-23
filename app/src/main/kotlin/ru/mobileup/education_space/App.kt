package ru.mobileup.education_space

import android.app.Application
import me.aartikov.replica.devtools.ReplicaDevTools
import org.koin.android.ext.koin.androidContext
import org.koin.core.Koin
import org.koin.dsl.koinApplication
import ru.mobileup.core.ComponentFactory
import ru.mobileup.core.KoinProvider
import ru.mobileup.core.DebugToolsInitializer
import timber.log.Timber

class App : Application(), KoinProvider {

    override lateinit var koin: Koin
        private set

    override fun onCreate() {
        super.onCreate()
        initLogger()
        koin = createKoin().also {
            it.declare(ComponentFactory(it))
        }
        initDebugTools()
    }

    private fun initLogger() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun createKoin(): Koin {
        return koinApplication {
            androidContext(this@App)
            modules(allModules)
        }.koin
    }

    private fun launchReplicaDevTools() {
        val devtools = koin.get<ReplicaDevTools>()
        devtools.launch()
    }

    private fun initDebugTools() {
        launchReplicaDevTools()
        DebugToolsInitializer.initialize(this)
    }
}