package com.playbowdogs.neighbors_dogsitter_android.data.model

/**
 * Class exposing authenticated chosen camera to the UI.
 */
class ChosenClip internal constructor(value: RecordedClipsResponse.Result?) {
    companion object {
        private var _value: RecordedClipsResponse.Result? = null
        val value: RecordedClipsResponse.Result?
            get() = _value
    }

    //... other data fields that may be accessible to the UI
    init {
        Companion._value = value
    }
}