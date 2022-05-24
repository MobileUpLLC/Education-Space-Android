package ru.mobileup.features.pin_code.domain

import ru.mobileup.features.pin_code.data.PinCodeStorage

class GetPinCodeInteractor(private val pinCodeStorage: PinCodeStorage) {

    suspend fun execute(): PinCode? {
        return pinCodeStorage.getPinCode()
    }
}