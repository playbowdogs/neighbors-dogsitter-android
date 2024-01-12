package com.playbowdogs.neighbors_dogsitter_android.data.model

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class LoggedInUserModel(
    @Json(name = "data")
    var `data`: Data = Data(),
    @Json(name = "message")
    var message: String = "",
    @Json(name = "success")
    var success: Int = 0
) {
    @Keep
    data class Data(
        @Json(name = "auth_token")
        var authToken: String = "",
        @Json(name = "created_at")
        var createdAt: String = "",
        @Json(name = "dog_profile")
        var dogProfile: List<DogProfile> = listOf(),
        @Json(name = "email")
        var email: String = "",
        @Json(name = "ewallet_amount")
        var ewalletAmount: Double = 0.0,
        @Json(name = "firstname")
        var firstname: String = "",
        @Json(name = "lastname")
        var lastname: String = "",
        @Json(name = "notification_status")
        var notificationStatus: Int = 0,
        @Json(name = "status")
        var status: Int = 0,
        @Json(name = "user_id")
        var userId: Int = 0
    ) {
        @Keep
        data class DogProfile(
            @Json(name = "active")
            var active: Int = 0,
            @Json(name = "breed_id")
            var breedId: Int = 0,
            @Json(name = "category_id")
            var categoryId: Int = 0,
            @Json(name = "created_at")
            var createdAt: String = "",
            @Json(name = "id")
            var id: Int = 0,
            @Json(name = "image")
            var image: String = "",
            @Json(name = "name")
            var name: String = "",
            @Json(name = "status")
            var status: Int = 0,
            @Json(name = "updated_at")
            var updatedAt: String = "",
            @Json(name = "user_id")
            var userId: Int = 0
        )
    }
}