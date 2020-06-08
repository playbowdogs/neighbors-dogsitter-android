package com.playbowdogs.neighbors_dogsitter_android.di

import com.playbowdogs.neighbors_dogsitter_android.data.repository.CameraListRepository
import com.playbowdogs.neighbors_dogsitter_android.ui.cameraList.CameraListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val cameraListModule: Module = module {
    viewModel { CameraListViewModel(get(), get()) }
    single { CameraListRepository(get()) }
}