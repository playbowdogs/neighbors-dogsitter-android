package com.playbowdogs.neighbors_dogsitter_android.ui.cameraList

import androidx.lifecycle.liveData
import com.playbowdogs.neighbors_dogsitter_android.data.repository.CameraListRepository
import com.playbowdogs.neighbors_dogsitter_android.utils.BaseViewModel
import com.playbowdogs.neighbors_dogsitter_android.utils.Resource
import kotlinx.coroutines.CoroutineScope

class CameraListViewModel(private val repo: CameraListRepository,
                          scope: CoroutineScope
) : BaseViewModel(scope) {
    fun getCamerasList() = liveData(scope.coroutineContext) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = repo.getCameraList("PersonalAccessToken afec708ac67fbccaa1a9b1c3ec3c31a34d740879")))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}