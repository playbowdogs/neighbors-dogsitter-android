package com.playbowdogs.neighbors_dogsitter_android.di

import com.playbowdogs.neighbors_dogsitter_android.data.repository.RecordedClipsListRepository
import com.playbowdogs.neighbors_dogsitter_android.ui.recordedClipsList.RecordedClipsListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val recordedClipsListModule: Module = module {
    viewModel { RecordedClipsListViewModel(get()) }
    single { RecordedClipsListRepository(get()) }
}