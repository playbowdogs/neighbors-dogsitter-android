package com.playbowdogs.neighbors_dogsitter_android.data.repository

import com.playbowdogs.neighbors_dogsitter_android.data.api.ApiService

class CameraListRepository(private val apiService: ApiService) {
    suspend fun getCameraList(authorization: String) = apiService.getCameraList(authorization)
}