package ru.mobileup.features.launchers.data

import retrofit2.http.GET

interface LauncherApi {

    @GET("/v4/launches/upcoming")
    suspend fun getUpcomingLaunchers(): List<LauncherResponse>
}