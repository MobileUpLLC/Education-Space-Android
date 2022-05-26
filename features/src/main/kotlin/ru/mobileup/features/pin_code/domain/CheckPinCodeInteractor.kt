package ru.mobileup.features.pin_code.domain

import ru.mobileup.features.pin_code.data.PinCodeStorage

class CheckPinCodeInteractor(private val pinCodeStorage: PinCodeStorage) {

    suspend fun execute(pinCode: PinCode): Boolean {
        return pinCode == pinCodeStorage.getPinCode()
    }
}