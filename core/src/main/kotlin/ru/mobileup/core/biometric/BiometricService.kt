package ru.mobileup.core.biometric

import kotlinx.coroutines.flow.Flow

interface BiometricService {

    val fingerprintResultFlow: Flow<BiometricAuthenticationResult>

    suspend fun startFingerprintAuthenticate()

    fun isFingerprintSupport(): Boolean
}