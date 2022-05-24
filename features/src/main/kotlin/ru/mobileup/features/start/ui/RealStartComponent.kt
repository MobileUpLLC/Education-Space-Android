package ru.mobileup.features.start.ui

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnStart
import ru.mobileup.core.error_handling.ErrorHandler
import ru.mobileup.core.utils.componentCoroutineScope
import ru.mobileup.core.error_handling.safeLaunch
import ru.mobileup.features.pin_code.domain.IsPinCodeSetInteractor

class RealStartComponent(
    componentContext: ComponentContext,
    private val onOutput: (StartComponent.Output) -> Unit,
    private val errorHandler: ErrorHandler,
    private val isPinCodeSetInteractor: IsPinCodeSetInteractor
) : ComponentContext by componentContext, StartComponent {

    private val coroutineScope = componentCoroutineScope()

    init {
        lifecycle.doOnStart {
            setupInitialScreen()
        }
    }

    private fun setupInitialScreen() {
        coroutineScope.safeLaunch(errorHandler) {
            when {
                !isPinCodeSetInteractor.execute() -> onOutput(StartComponent.Output.PinCodeRequested)
                else -> onOutput(StartComponent.Output.HomeRequested)
            }
        }
    }
}