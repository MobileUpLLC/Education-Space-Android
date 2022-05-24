package ru.mobileup.core.data_cleaner

class CleanDataInteractor(private val cleaners: List<DataCleaner>) {

    suspend fun execute() {
        cleaners.forEach { it.clean() }
    }
}