package com.playbowdogs.neighbors_dogsitter_android.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.playbowdogs.neighbors_dogsitter_android.data.api.PBApiService
import com.playbowdogs.neighbors_dogsitter_android.data.model.LoggedInUserModel
import com.playbowdogs.neighbors_dogsitter_android.data.model.PostLogin
import com.playbowdogs.neighbors_dogsitter_android.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

class LoginViewModel : ViewModel(), KoinComponent {
    private val pbApiService: PBApiService by inject()

    val email: MutableLiveData<String> = MutableLiveData()
    val password: MutableLiveData<String> = MutableLiveData()
    val androidDeviceToken: MutableLiveData<String> = MutableLiveData()

    val userModel: MutableLiveData<Resource<LoggedInUserModel>> = MutableLiveData()

    fun postLogin() = GlobalScope.launch(Dispatchers.IO) {
        val postLogin: PostLogin = PostLogin(
            username = email.value.toString(),
            password = password.value.toString(),
            androidDeviceToken = "",
            iosDeviceToken = ""
        )

        userModel.postValue(Resource.loading(data = null))
        try {
            userModel.postValue(Resource.success(data = pbApiService.getLogin(postLogin)))
        } catch (exception: Exception) {
            userModel.postValue(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}