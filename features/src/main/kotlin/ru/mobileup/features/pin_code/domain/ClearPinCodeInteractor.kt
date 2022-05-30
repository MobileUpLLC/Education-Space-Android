package ru.mobileup.features.pin_code.domain

import ru.mobileup.features.pin_code.data.PinCodeStorage

class ClearPinCodeInteractor(private val pinCodeStorage: PinCodeStorage) {

    suspend fun execute() {
        pinCodeStorage.clearPinCode()
    }
}