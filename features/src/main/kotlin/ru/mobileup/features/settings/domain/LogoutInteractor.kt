package ru.mobileup.features.settings.domain

import ru.mobileup.core.data_cleaner.CleanDataInteractor

class LogoutInteractor(private val cleanDataInteractor: CleanDataInteractor) {

    suspend fun execute() {
        // Чистим закешированные данные
        cleanDataInteractor.execute()
    }
}