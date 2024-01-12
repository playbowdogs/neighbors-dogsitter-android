package com.playbowdogs.neighbors_dogsitter_android.data.model


import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class CreateClipResponse(
    @Json(name = "id")
    var id: String = "",
    @Json(name = "name")
    var name: String = "",
    @Json(name = "status")
    var status: String = "",
    @Json(name = "sharing_token")
    var sharingToken: String = "",
    @Json(name = "start")
    var start: String = "",
    @Json(name = "end")
    var end: String = "",
    @Json(name = "created_at")
    var createdAt: String = "",
    @Json(name = "download_url")
    var downloadUrl: String = ""
)