package com.playbowdogs.neighbors_dogsitter_android.data.model


import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class RecordedClipsResponse(
    @Json(name = "count")
    var count: Int = 0,
    @Json(name = "next")
    var next: Any? = null,
    @Json(name = "previous")
    var previous: Any? = null,
    @Json(name = "results")
    var results: List<Result> = listOf()
) {
    @Keep
    data class Result(
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
        @Json(name = "stream")
        var stream: Stream = Stream(),
        @Json(name = "download_url")
        var downloadUrl: String = ""
    ) {
        @Keep
        data class Stream(
            @Json(name = "format")
            var format: String = "",
            @Json(name = "url")
            var url: String = ""
        )
    }
}