package ru.mobileup.features.pin_code.domain

import ru.mobileup.features.pin_code.data.PinCodeStorage

class IsPinCodeSetInteractor(private val pinCodeStorage: PinCodeStorage) {

    suspend fun execute(): Boolean {
        return pinCodeStorage.getPinCode() != null
    }
}