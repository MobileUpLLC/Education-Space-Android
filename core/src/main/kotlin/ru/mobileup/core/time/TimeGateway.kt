package ru.mobileup.core.time

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone

interface TimeGateway {

    val currentTime: Instant

    val timeZone: TimeZone
}