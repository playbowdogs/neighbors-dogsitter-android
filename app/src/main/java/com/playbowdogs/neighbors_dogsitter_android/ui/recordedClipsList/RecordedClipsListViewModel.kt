package com.playbowdogs.neighbors_dogsitter_android.ui.recordedClipsList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.playbowdogs.neighbors_dogsitter_android.data.model.ChosenCamera
import com.playbowdogs.neighbors_dogsitter_android.data.repository.RecordedClipsListRepository
import com.playbowdogs.neighbors_dogsitter_android.utils.Resource
import kotlinx.coroutines.Dispatchers

class RecordedClipsListViewModel(private val repo: RecordedClipsListRepository) : ViewModel() {

    fun getRecordedClips() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = repo.getRecordedClips(
                authorization = "PersonalAccessToken afec708ac67fbccaa1a9b1c3ec3c31a34d740879",
                camera_id = ChosenCamera.value?.id.toString())))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}