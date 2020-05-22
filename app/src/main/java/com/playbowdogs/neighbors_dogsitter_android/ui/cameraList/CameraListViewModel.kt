package com.playbowdogs.neighbors_dogsitter_android.ui.cameraList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.playbowdogs.neighbors_dogsitter_android.data.api.ApiService
import com.playbowdogs.neighbors_dogsitter_android.utils.Resource
import kotlinx.coroutines.Dispatchers
import org.koin.core.KoinComponent
import org.koin.core.inject

class CameraListViewModel : ViewModel(), KoinComponent {
    private val apiService: ApiService by inject()

    fun getCamerasList() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = apiService.getCameraList("PersonalAccessToken afec708ac67fbccaa1a9b1c3ec3c31a34d740879")))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}