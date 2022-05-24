package ru.mobileup.core.biometric

sealed interface BiometricAuthenticationResult {
    object Succeeded : BiometricAuthenticationResult
    object Error : BiometricAuthenticationResult
    object Canceled : BiometricAuthenticationResult
}