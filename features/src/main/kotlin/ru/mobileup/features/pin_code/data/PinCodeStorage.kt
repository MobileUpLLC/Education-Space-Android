package ru.mobileup.features.pin_code.data

import ru.mobileup.features.pin_code.domain.PinCode

interface PinCodeStorage {

    suspend fun savePinCode(pinCode: PinCode)

    suspend fun getPinCode(): PinCode?

    suspend fun clearPinCode()
}