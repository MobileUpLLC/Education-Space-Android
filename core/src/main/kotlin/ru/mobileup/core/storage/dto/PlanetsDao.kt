package ru.mobileup.core.storage.dto

import androidx.room.Dao
import androidx.room.Query

@Dao
interface PlanetsDao {

    @Query("SELECT * FROM planets")
    suspend fun getAllPlanets(): List<PlanetDb>
}