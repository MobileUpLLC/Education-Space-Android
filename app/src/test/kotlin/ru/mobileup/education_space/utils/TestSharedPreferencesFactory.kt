package ru.mobileup.education_space.utils

import android.content.Context
import android.content.SharedPreferences
import ru.mobileup.core.storage.SharedPreferencesFactory

class TestSharedPreferencesFactory : SharedPreferencesFactory {

    /**
     * При попытки создать реальный EncryptedSharedPreferences вернется исключение
     * от Robolectric о том, что не найден AndroidKeyStore.
     * Однако, если исключить момент шифрования, EncryptedSharedPreferences
     * является аналогом SharedPreferences, по этой причине
     * SharedPreferences может выступить заменой EncryptedSharedPreferences для тестов
     */
    override fun createEncryptedPreferences(
        context: Context,
        alias: String,
        fileName: String
    ): SharedPreferences {
        return context.getSharedPreferences(fileName, Context.MODE_PRIVATE)
    }

    override fun createPreferences(context: Context, prefsName: String): SharedPreferences {
        return context.getSharedPreferences(prefsName, Context.MODE_PRIVATE)
    }
}