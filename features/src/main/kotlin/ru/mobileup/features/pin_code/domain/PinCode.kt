package ru.mobileup.features.pin_code.domain

@JvmInline
value class PinCode(val value: String) {

    init {
        check(value.length == PIN_CODE_LENGTH)
    }

    companion object {
        const val PIN_CODE_LENGTH = 4
    }
}