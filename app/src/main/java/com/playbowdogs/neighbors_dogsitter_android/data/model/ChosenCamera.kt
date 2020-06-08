package com.playbowdogs.neighbors_dogsitter_android.data.model

/**
 * Class exposing chosen camera to the UI.
 */
class ChosenCamera internal constructor(value: AccountCamerasModel.Result?) {
    companion object {
        private var _value: AccountCamerasModel.Result? = null
        val value: AccountCamerasModel.Result?
            get() = _value
    }

    //... other data fields that may be accessible to the UI
    init {
        Companion._value = value
    }
}