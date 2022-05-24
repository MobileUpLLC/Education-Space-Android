package ru.mobileup.education_space.utils

import android.content.Context
import androidx.room.Room
import ru.mobileup.core.storage.BaseRoomDatabase
import ru.mobileup.core.storage.RoomDatabaseFactory

class TestRoomDatabaseFactory : RoomDatabaseFactory {

    override fun createDatabaseInstance(context: Context): BaseRoomDatabase {
        val builder = Room.inMemoryDatabaseBuilder(context, BaseRoomDatabase::class.java)
        return BaseRoomDatabase.getDatabase(context, builder)
    }
}