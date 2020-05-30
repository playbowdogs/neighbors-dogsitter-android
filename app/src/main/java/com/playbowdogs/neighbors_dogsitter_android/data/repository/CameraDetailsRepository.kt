package com.playbowdogs.neighbors_dogsitter_android.data.repository

import com.playbowdogs.neighbors_dogsitter_android.data.api.ApiService

class CameraDetailsRepository(private val apiService: ApiService) {

    suspend fun getCameraStatus(
        authorization: String,
        camera_id: String
    ) = apiService.getCameraStatus(authorization, camera_id)

    suspend fun getRecordingInfo(
        authorization: String,
        camera_id: String
    ) = apiService.getRecordingInfo(authorization, camera_id)
}