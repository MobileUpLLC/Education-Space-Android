package ru.mobileup.core.storage

import android.content.Context
import ru.mobileup.core.storage.BaseRoomDatabase

interface RoomDatabaseFactory {

    fun createDatabaseInstance(context: Context): BaseRoomDatabase
}