package com.playbowdogs.neighbors_dogsitter_android.data.model


import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class CreateClipModel(
    @Json(name = "name")
    var name: String = "",
    @Json(name = "start")
    var start: String = "",
    @Json(name = "end")
    var end: String = ""
)