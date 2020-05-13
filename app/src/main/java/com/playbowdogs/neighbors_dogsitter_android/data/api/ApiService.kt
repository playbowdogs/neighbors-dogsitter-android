package com.playbowdogs.neighbors_dogsitter_android.data.api

import com.playbowdogs.neighbors_dogsitter_android.data.model.AccountCamerasModel
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @GET("cameras/")
    suspend fun getCameras(@Header(value = "Authorization")authorization: String): AccountCamerasModel

    @POST("cameras/{camera_id}/broadcasting/start/")
    suspend fun startBroadcasting(
        @Header(value = "Authorization") authorization: String,
        @Path(value = "camera_id", encoded = true) camera_id: String): AccountCamerasModel
}