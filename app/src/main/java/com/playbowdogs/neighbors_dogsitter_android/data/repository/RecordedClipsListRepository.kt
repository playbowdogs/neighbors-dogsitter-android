package com.playbowdogs.neighbors_dogsitter_android.data.repository

import com.playbowdogs.neighbors_dogsitter_android.data.api.ApiService

class RecordedClipsListRepository(private val apiService: ApiService) {
    suspend fun getRecordedClips(
        authorization: String,
        camera_id: String
    ) = apiService.getRecordedClips(authorization, camera_id)
}