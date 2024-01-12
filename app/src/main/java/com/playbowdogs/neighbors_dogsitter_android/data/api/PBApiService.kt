package com.playbowdogs.neighbors_dogsitter_android.data.api

import com.playbowdogs.neighbors_dogsitter_android.data.model.LoggedInUserModel
import com.playbowdogs.neighbors_dogsitter_android.data.model.PostLogin
import retrofit2.http.Body
import retrofit2.http.POST

interface PBApiService {

    @POST("login/")
    suspend fun getLogin(@Body postLogin: PostLogin)  : LoggedInUserModel
}