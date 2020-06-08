package com.playbowdogs.neighbors_dogsitter_android.di

import com.playbowdogs.neighbors_dogsitter_android.data.repository.LoginRepository
import com.playbowdogs.neighbors_dogsitter_android.ui.login.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val loginModule: Module = module {
    viewModel { LoginViewModel(get()) }
    single { LoginRepository(get(), get(), get()) }
}