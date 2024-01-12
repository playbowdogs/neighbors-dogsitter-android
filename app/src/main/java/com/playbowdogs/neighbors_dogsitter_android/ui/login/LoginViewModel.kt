package com.playbowdogs.neighbors_dogsitter_android.ui.login

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import com.playbowdogs.neighbors_dogsitter_android.R
import com.playbowdogs.neighbors_dogsitter_android.data.formState.LoginFormState
import com.playbowdogs.neighbors_dogsitter_android.data.model.LoggedInUserModel
import com.playbowdogs.neighbors_dogsitter_android.data.model.PostLogin
import com.playbowdogs.neighbors_dogsitter_android.data.repository.LoginRepository
import com.playbowdogs.neighbors_dogsitter_android.utils.BaseViewModel
import com.playbowdogs.neighbors_dogsitter_android.utils.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginViewModel(private val repo: LoginRepository,
                     scope: CoroutineScope
) : BaseViewModel(scope) {
    val sharedPrefAuthToken: MutableLiveData<String> = MutableLiveData()
    val loginFormState: MutableLiveData<LoginFormState> = MutableLiveData()

    val email: MutableLiveData<String> = MutableLiveData()
    val password: MutableLiveData<String> = MutableLiveData()
    val androidDeviceToken: MutableLiveData<String> = MutableLiveData()

    val userModel: MutableLiveData<Resource<LoggedInUserModel>> = MutableLiveData()

    fun postLogin() = scope.launch {
        val postLogin = PostLogin(
            email = email.value.toString(),
            password = password.value.toString(),
            androidDeviceToken = "",
            iosDeviceToken = ""
        )

        userModel.postValue(Resource.loading(data = null))
        try {
            userModel.postValue(Resource.success(data = repo.getLogin(postLogin)))
        } catch (exception: Exception) {
            userModel.postValue(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun checkAuthToken() = scope.launch {
        if (repo.returnAuthToken() != "") {
            sharedPrefAuthToken.postValue(repo.returnAuthToken())
        } else { Log.e("User", "No user!") }
    }

    fun saveAuthToken(authToken: String) = GlobalScope.launch(Dispatchers.IO) {
        repo.saveAuthToken(authToken)
    }

    fun loginDataChanged(username: String?, password: String?) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(LoginFormState(R.string.invalid_username, null))
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(LoginFormState(null, R.string.invalid_password))
        } else {
            loginFormState.setValue(LoginFormState(true))
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String?): Boolean {
        if (username == null) {
            return false
        }
        return if (username.contains("@")) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.trim { it <= ' ' }.isNotEmpty()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String?): Boolean {
        return password != null && password.trim { it <= ' ' }.length > 5
    }

}