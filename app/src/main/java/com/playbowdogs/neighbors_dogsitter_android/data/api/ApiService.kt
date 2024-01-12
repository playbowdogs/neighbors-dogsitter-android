package com.playbowdogs.neighbors_dogsitter_android.data.api

import com.playbowdogs.neighbors_dogsitter_android.data.model.*
import retrofit2.http.*

interface ApiService {

    @GET("cameras/")
    suspend fun getCameraList(@Header(value = "Authorization") authorization: String
    ): AccountCamerasModel

    @GET("cameras/{camera_id}/")
    suspend fun getCameraStatus(
        @Header(value = "Authorization") authorization: String,
        @Path(value = "camera_id", encoded = true) camera_id: String
    ): AccountCamerasModel.Result

    @GET("cameras/{camera_id}/recording/")
    suspend fun getRecordingInfo(
        @Header(value = "Authorization") authorization: String,
        @Path(value = "camera_id", encoded = true) camera_id: String
    ): GetRecordingInfoModel

    @POST("cameras/{camera_id}/clips/")
    suspend fun createClip(
        @Header(value = "Authorization") authorization: String,
        @Path(value = "camera_id", encoded = true) camera_id: String,
        @Body()createClipModel: CreateClipModel
    ): CreateClipResponse

    @GET("cameras/{camera_id}/clips/")
    suspend fun getRecordedClips(
        @Header(value = "Authorization") authorization: String,
        @Path(value = "camera_id", encoded = true) camera_id: String
    ): RecordedClipsResponse
}