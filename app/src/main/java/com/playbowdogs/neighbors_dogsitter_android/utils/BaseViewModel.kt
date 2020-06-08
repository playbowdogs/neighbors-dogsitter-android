package com.playbowdogs.neighbors_dogsitter_android.utils

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel

abstract class BaseViewModel(protected val scope: CoroutineScope) : ViewModel() {

    override fun onCleared() {
        scope.cancel()
    }
}