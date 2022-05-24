package ru.mobileup.features.pin_code.data

import ru.mobileup.core.data_cleaner.DataCleaner

class PinCodeCleaner(private val pinCodeStorage: PinCodeStorage) : DataCleaner {

    override suspend fun clean() {
        pinCodeStorage.clearPinCode()
    }
}