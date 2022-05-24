package ru.mobileup.core.storage

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences

class RealSharedPreferencesFactory : SharedPreferencesFactory {

    override fun createEncryptedPreferences(
        context: Context,
        alias: String,
        fileName: String
    ): SharedPreferences {
        return EncryptedSharedPreferences.create(
            fileName,
            alias,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    override fun createPreferences(context: Context, prefsName: String): SharedPreferences {
        return context.getSharedPreferences(prefsName, Context.MODE_PRIVATE)
    }
}