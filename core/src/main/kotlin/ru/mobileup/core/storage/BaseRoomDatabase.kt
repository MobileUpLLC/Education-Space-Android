package ru.mobileup.core.storage

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import ru.mobileup.core.storage.dto.PlanetDb
import ru.mobileup.core.storage.dto.PlanetsDao

@OptIn(ExperimentalSerializationApi::class)
@Database(
    entities = [PlanetDb::class],
    version = 1,
    exportSchema = false
)
abstract class BaseRoomDatabase : RoomDatabase() {

    companion object {
        private const val PLANETS_FILE_NAME = "planets.json"

        fun <T : RoomDatabase> getDatabase(context: Context, builder: Builder<T>) = builder
            .addCallback(object : Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    val inputStream = context.assets.open(PLANETS_FILE_NAME)
                    Json.decodeFromStream<List<PlanetDb>>(inputStream)
                        .forEach {
                            val contentValues = ContentValues().apply {
                                put("id", it.id)
                                put("name", it.name)
                                put("description", it.description)
                            }
                            db.insert("planets", SQLiteDatabase.CONFLICT_REPLACE, contentValues)
                        }
                }
            })
            .build()
    }

    abstract fun getPlanetsDao(): PlanetsDao
}