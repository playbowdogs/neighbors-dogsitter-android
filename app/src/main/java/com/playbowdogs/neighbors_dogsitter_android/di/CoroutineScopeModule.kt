package com.playbowdogs.neighbors_dogsitter_android.di

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.koin.core.module.Module
import org.koin.dsl.module

val coroutineScopeModule: Module = module {
    single { CoroutineScope(Job() + Dispatchers.IO) }
}