package com.playbowdogs.neighbors_dogsitter_android.data.repository

import android.content.SharedPreferences
import com.playbowdogs.neighbors_dogsitter_android.data.api.PBApiService
import com.playbowdogs.neighbors_dogsitter_android.data.model.PostLogin
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoginRepository(
    private val apiService: PBApiService,
    private val sharedPreferences: SharedPreferences,
    private val sharedPreferencesEditor: SharedPreferences.Editor
) {
    suspend fun getLogin(postLogin: PostLogin) = apiService.getLogin(postLogin)

    suspend fun returnAuthToken() : String {
        return withContext(Dispatchers.IO) {
            return@withContext sharedPreferences.getString("authToken", "") ?: ""
        }
    }

    suspend fun saveAuthToken(authToken: String) : String {
        withContext(Dispatchers.IO) {
            sharedPreferencesEditor
                .putString("authToken", authToken)
                .commit()
        }
        return returnAuthToken()
    }
}