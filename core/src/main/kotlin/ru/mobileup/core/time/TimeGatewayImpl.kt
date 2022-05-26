package ru.mobileup.core.time

import kotlinx.datetime.*

class TimeGatewayImpl : TimeGateway {

    override val currentTime: Instant
        get() = Clock.System.now()

    override val timeZone: TimeZone
        get() = TimeZone.currentSystemDefault()
}