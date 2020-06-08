package com.playbowdogs.neighbors_dogsitter_android

import android.app.Application
import com.playbowdogs.neighbors_dogsitter_android.di.*
import com.playbowdogs.neighbors_dogsitter_android.utils.DefaultCurrentActivityListener
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class NeighborsDogSitter : Application() {
    private val defaultCurrentActivityListener : DefaultCurrentActivityListener by inject()
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@NeighborsDogSitter)
            modules(listOf(
                appModule,
                networkModule,
                sharedPrefModule,
                cameraDetailsModule,
                cameraListModule,
                loginModule,
                recordedClipsListModule
            ))
        }
        registerActivityLifecycleCallbacks(defaultCurrentActivityListener)
    }
}