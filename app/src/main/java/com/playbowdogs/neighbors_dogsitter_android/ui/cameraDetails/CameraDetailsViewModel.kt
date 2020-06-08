package com.playbowdogs.neighbors_dogsitter_android.ui.cameraDetails

import androidx.lifecycle.MutableLiveData
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.Headers
import com.playbowdogs.neighbors_dogsitter_android.data.model.AccountCamerasModel
import com.playbowdogs.neighbors_dogsitter_android.data.model.ChosenCamera
import com.playbowdogs.neighbors_dogsitter_android.data.model.GetRecordingInfoModel
import com.playbowdogs.neighbors_dogsitter_android.data.repository.CameraDetailsRepository
import com.playbowdogs.neighbors_dogsitter_android.utils.BaseViewModel
import com.playbowdogs.neighbors_dogsitter_android.utils.Resource
import kotlinx.coroutines.*

class CameraDetailsViewModel(private val repo: CameraDetailsRepository,
                             scope: CoroutineScope
) : BaseViewModel(scope) {
    val cameraStatus = MutableLiveData<Resource<AccountCamerasModel.Result>>()
    val cameraRecordingInfo = MutableLiveData<Resource<GetRecordingInfoModel>>()
    private val cameraID = ChosenCamera.value?.id.toString()

    fun getCameraInfo() {
        getCameraStatus()
        getRecordingInfo()
    }

    private fun getCameraStatus() = scope.launch {
        cameraStatus.postValue(Resource.loading(data = null))
        try {
            cameraStatus.postValue(
                Resource.success(data = repo.getCameraStatus(
                "PersonalAccessToken afec708ac67fbccaa1a9b1c3ec3c31a34d740879",
                ChosenCamera.value?.id.toString())))
        } catch (exception: Exception) {
            cameraStatus.postValue(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun getRecordingInfo() = scope.launch {
        cameraRecordingInfo.postValue(Resource.loading(data = null))
        try {
            cameraRecordingInfo.postValue(
                Resource.success(data = repo.getRecordingInfo(
                "PersonalAccessToken afec708ac67fbccaa1a9b1c3ec3c31a34d740879",
                ChosenCamera.value?.id.toString())))
        } catch (exception: Exception) {
            cameraRecordingInfo.postValue(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun startRecording() = scope.launch {
        cameraRecordingInfo.postValue(Resource.loading(data = null))

        val task = GlobalScope.async {
            val (_, _, _) =
                Fuel.post("https://api.angelcam.com/v1/cameras/$cameraID/recording/start/")
                    .header(
                        Headers.AUTHORIZATION,
                        "PersonalAccessToken afec708ac67fbccaa1a9b1c3ec3c31a34d740879"
                    )
                    .response()
        }
        task.await()
        delay(1000)
        getRecordingInfo()
    }

    fun stopRecording() = scope.launch {
        cameraRecordingInfo.postValue(Resource.loading(data = null))

        val task = GlobalScope.async {
            val (_, _, _) =
                Fuel.post("https://api.angelcam.com/v1/cameras/$cameraID/recording/stop/")
                    .header(
                        Headers.AUTHORIZATION,
                        "PersonalAccessToken afec708ac67fbccaa1a9b1c3ec3c31a34d740879"
                    )
                    .response()
        }
        task.await()
        delay(1000)
        getRecordingInfo()
    }
}