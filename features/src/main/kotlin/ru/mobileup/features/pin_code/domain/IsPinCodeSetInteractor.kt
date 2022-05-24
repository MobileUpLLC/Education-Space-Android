package ru.mobileup.features.pin_code.domain

class IsPinCodeSetInteractor(private val getPinCodeInteractor: GetPinCodeInteractor) {

    suspend fun execute(): Boolean {

        return getPinCodeInteractor.execute() != null
    }
}