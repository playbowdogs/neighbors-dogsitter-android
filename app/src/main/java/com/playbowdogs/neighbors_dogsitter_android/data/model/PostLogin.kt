package com.playbowdogs.neighbors_dogsitter_android.data.model

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class PostLogin(
    @Json(name = "email")
    var email: String = "",
    @Json(name = "password")
    var password: String = "",
    @Json(name = "androidDeviceToken")
    var androidDeviceToken: String = "",
    @Json(name = "iosDeviceToken")
    var iosDeviceToken: String = ""
)