package com.playbowdogs.neighbors_dogsitter_android.di

import com.playbowdogs.neighbors_dogsitter_android.data.repository.CameraDetailsRepository
import com.playbowdogs.neighbors_dogsitter_android.ui.cameraDetails.CameraDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val cameraDetailsModule : Module = module {
    viewModel { CameraDetailsViewModel(get()) }
    single { CameraDetailsRepository(get()) }
}