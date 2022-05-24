package ru.mobileup.core.storage.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "planets")
data class PlanetDb(
    @PrimaryKey
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("description") val description: String
)