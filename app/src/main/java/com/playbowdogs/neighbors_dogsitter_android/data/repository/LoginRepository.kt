package com.playbowdogs.neighbors_dogsitter_android.data.repository

import com.playbowdogs.neighbors_dogsitter_android.data.api.PBApiService
import com.playbowdogs.neighbors_dogsitter_android.data.model.PostLogin

class LoginRepository(private val apiService: PBApiService) {
    suspend fun getLogin(postLogin: PostLogin) = apiService.getLogin(postLogin)
}