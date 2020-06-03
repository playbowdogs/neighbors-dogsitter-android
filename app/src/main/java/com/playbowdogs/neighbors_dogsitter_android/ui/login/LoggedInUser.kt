package com.playbowdogs.neighbors_dogsitter_android.ui.login

import com.playbowdogs.neighbors_dogsitter_android.data.model.LoggedInUserModel

/**
 * Class exposing authenticated user details to the UI.
 */
class LoggedInUser internal constructor(dataBean: LoggedInUserModel.Data?) {
    companion object {
        private var dataBean: LoggedInUserModel.Data? = null
        val databean: LoggedInUserModel.Data?
            get() = dataBean
    }

    //... other data fields that may be accessible to the UI
    init {
        Companion.dataBean = dataBean
    }
}