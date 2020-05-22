package com.playbowdogs.neighbors_dogsitter_android.ui.cameraDetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.Headers
import com.playbowdogs.neighbors_dogsitter_android.data.api.ApiService
import com.playbowdogs.neighbors_dogsitter_android.data.model.AccountCamerasModel
import com.playbowdogs.neighbors_dogsitter_android.data.model.ChosenCamera
import com.playbowdogs.neighbors_dogsitter_android.data.model.GetRecordingInfoModel
import com.playbowdogs.neighbors_dogsitter_android.utils.Resource
import kotlinx.coroutines.*
import org.koin.core.KoinComponent
import org.koin.core.inject

class CameraDetailsViewModel : ViewModel(), KoinComponent {
    private val apiService: ApiService by inject()

    val cameraStatus = MutableLiveData<Resource<AccountCamerasModel.Result>>()
    val cameraRecordingInfo = MutableLiveData<Resource<GetRecordingInfoModel>>()
    private val cameraID = ChosenCamera.value?.id.toString()

    fun getCameraInfo() {
        getCameraStatus()
        getRecordingInfo()
    }

    private fun getCameraStatus() = GlobalScope.launch(Dispatchers.IO) {
        cameraStatus.postValue(Resource.loading(data = null))
        try {
            cameraStatus.postValue(
                Resource.success(data = apiService.getCameraStatus(
                "PersonalAccessToken afec708ac67fbccaa1a9b1c3ec3c31a34d740879",
                ChosenCamera.value?.id.toString())))
        } catch (exception: Exception) {
            cameraStatus.postValue(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun getRecordingInfo() = GlobalScope.launch(Dispatchers.IO) {
        cameraRecordingInfo.postValue(Resource.loading(data = null))
        try {
            cameraRecordingInfo.postValue(
                Resource.success(data = apiService.getRecordingInfo(
                "PersonalAccessToken afec708ac67fbccaa1a9b1c3ec3c31a34d740879",
                ChosenCamera.value?.id.toString())))
        } catch (exception: Exception) {
            cameraRecordingInfo.postValue(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun startRecording() = GlobalScope.launch(Dispatchers.IO) {
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

    fun stopRecording() = GlobalScope.launch(Dispatchers.IO) {
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