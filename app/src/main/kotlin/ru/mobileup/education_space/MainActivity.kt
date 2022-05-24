package ru.mobileup.education_space

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.fragment.app.FragmentActivity
import com.arkivanov.decompose.defaultComponentContext
import com.arkivanov.essenty.lifecycle.asEssentyLifecycle
import com.arkivanov.essenty.lifecycle.doOnDestroy
import ru.mobileup.core.ComponentFactory
import ru.mobileup.core.activity.ActivityProvider
import ru.mobileup.core.koin
import ru.mobileup.features.root.createRootComponent
import ru.mobileup.features.root.ui.RootUi

class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activityProvider = application.koin.get<ActivityProvider>()
        activityProvider.attachActivity(this)
        lifecycle.asEssentyLifecycle().doOnDestroy {
            activityProvider.detachActivity()
        }

        val componentFactory = application.koin.get<ComponentFactory>()
        val rootComponent = componentFactory.createRootComponent(defaultComponentContext())

        setContent {
            RootUi(rootComponent)
        }
    }
}