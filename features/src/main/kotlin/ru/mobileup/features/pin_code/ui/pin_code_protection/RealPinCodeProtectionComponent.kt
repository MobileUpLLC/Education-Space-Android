package ru.mobileup.features.pin_code.ui.pin_code_protection

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnStart
import com.arkivanov.essenty.lifecycle.doOnStop
import kotlinx.datetime.Instant
import ru.mobileup.core.time.TimeGateway
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class RealPinCodeProtectionComponent(
    componentContext: ComponentContext,
    private val onOutput: (PinCodeProtectionComponent.Output) -> Unit,
    timeGateway: TimeGateway
) : ComponentContext by componentContext, PinCodeProtectionComponent {

    companion object {
        private val PROTECTION_TIMEOUT: Duration = 30L.toDuration(DurationUnit.SECONDS)
    }

    private var backgroundStartTime: Instant? = null

    init {
        lifecycle.doOnStart {
            if (backgroundStartTime != null) {
                val currentMoment = timeGateway.currentTime
                val difference = currentMoment.minus(backgroundStartTime!!)
                if (difference >= PROTECTION_TIMEOUT) {
                    onOutput(PinCodeProtectionComponent.Output.CheckPinCodeRequested)
                }
                backgroundStartTime = null
            }
        }
        lifecycle.doOnStop {
            backgroundStartTime = timeGateway.currentTime
        }
    }
}