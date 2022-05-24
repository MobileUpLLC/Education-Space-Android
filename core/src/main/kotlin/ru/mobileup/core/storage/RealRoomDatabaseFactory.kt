package ru.mobileup.core.storage

import android.content.Context
import androidx.room.Room

class RealRoomDatabaseFactory : RoomDatabaseFactory {

    override fun createDatabaseInstance(context: Context): BaseRoomDatabase {
        val builder = Room.databaseBuilder(
            context,
            BaseRoomDatabase::class.java,
            "education-space-database"
        )
        return BaseRoomDatabase.getDatabase(context, builder)
    }
}