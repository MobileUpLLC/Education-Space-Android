package ru.mobileup.features.pin_code.data

import android.content.SharedPreferences
import kotlinx.coroutines.Dispatchers
import androidx.core.content.edit
import ru.mobileup.features.pin_code.domain.PinCode

class PinCodeStorageImpl(private val prefs: SharedPreferences) : PinCodeStorage {

    companion object {
        private const val PIN_CODE_KEY = "PIN_CODE_KEY"
    }

    override suspend fun savePinCode(pinCode: PinCode) = with(Dispatchers.IO) {
        prefs.edit { putString(PIN_CODE_KEY, pinCode.value) }
    }

    override suspend fun getPinCode(): PinCode? = with(Dispatchers.IO) {
        return prefs.getString(PIN_CODE_KEY, null)?.let { PinCode(it) }
    }

    override suspend fun clearPinCode() = with(Dispatchers.IO) {
        prefs.edit { putString(PIN_CODE_KEY, null) }
    }
}