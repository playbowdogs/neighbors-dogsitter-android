package com.playbowdogs.neighbors_dogsitter_android.data.model


import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class ActivateRecordingModel(
    @Json(name = "status")
    var status: String = "",
    @Json(name = "retention")
    var retention: String = "",
    @Json(name = "active_service_id")
    var activeServiceId: Int = 0,
    @Json(name = "deactivated_at")
    var deactivatedAt: Any? = null
)