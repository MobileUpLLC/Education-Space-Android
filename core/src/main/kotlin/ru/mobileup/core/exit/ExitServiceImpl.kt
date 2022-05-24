package ru.mobileup.core.exit

import ru.mobileup.core.activity.ActivityProvider

class ExitServiceImpl(private val activityProvider: ActivityProvider) : ExitService {

    override fun closeApp() {
        val activity = activityProvider.activity ?: return
        activity.finish()
    }
}