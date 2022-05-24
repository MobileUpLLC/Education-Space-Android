package ru.mobileup.core.storage

import android.content.Context
import android.content.SharedPreferences

interface SharedPreferencesFactory {

    fun createPreferences(context: Context, prefsName: String): SharedPreferences

    fun createEncryptedPreferences(
        context: Context,
        alias: String,
        fileName: String
    ): SharedPreferences
}