package ru.mobileup.core.storage

import android.content.Context

interface RoomDatabaseFactory {

    fun createDatabaseInstance(context: Context): BaseRoomDatabase
}