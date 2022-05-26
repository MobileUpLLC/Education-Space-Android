package ru.mobileup.features.settings.domain

import ru.mobileup.features.pin_code.domain.ClearPinCodeInteractor

class LogoutInteractor(private val clearPinCodeInteractor: ClearPinCodeInteractor) {

    suspend fun execute() {
        // Чистим закешированные данные
        clearPinCodeInteractor.execute()
    }
}