package ru.mobileup.features.pin_code.domain

import ru.mobileup.features.pin_code.data.PinCodeStorage

class SavePinCodeInteractor(private val pinCodeStorage: PinCodeStorage) {

    suspend fun execute(pinCode: PinCode) {
        pinCodeStorage.savePinCode(pinCode)
    }
}