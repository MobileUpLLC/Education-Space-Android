package ru.mobileup.core.biometric

import android.content.Context
import android.content.pm.PackageManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import ru.mobileup.core.R
import ru.mobileup.core.activity.ActivityProvider

class BiometricServiceImpl(
    private val activityProvider: ActivityProvider,
    private val context: Context
) : BiometricService {

    private val channel = Channel<BiometricAuthenticationResult>(Channel.UNLIMITED)

    override val fingerprintResultFlow = channel.receiveAsFlow()

    override suspend fun startFingerprintAuthenticate() {
        val activity = activityProvider.awaitActivity()
        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle(context.getString(R.string.biometric_title))
            .setNegativeButtonText(context.getString(R.string.common_cancel))
            .build()

        val callback = object : BiometricPrompt.AuthenticationCallback() {

            override fun onAuthenticationSucceeded(
                result: BiometricPrompt.AuthenticationResult
            ) {
                super.onAuthenticationSucceeded(result)
                channel.trySend(BiometricAuthenticationResult.Succeeded)
            }

            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                channel.trySend(BiometricAuthenticationResult.Error)
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                channel.trySend(BiometricAuthenticationResult.Canceled)
            }
        }

        val executor = ContextCompat.getMainExecutor(context)
        val biometricPrompt = BiometricPrompt(activity, executor, callback)
        biometricPrompt.authenticate(promptInfo)
    }

    override fun isFingerprintSupport(): Boolean {
        val packageManager = context.packageManager
        return packageManager.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT)
    }
}