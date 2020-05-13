package com.playbowdogs.neighbors_dogsitter_android.di

import com.playbowdogs.neighbors_dogsitter_android.utils.ActivityRetriever
import com.playbowdogs.neighbors_dogsitter_android.utils.DefaultCurrentActivityListener
import org.koin.dsl.module

val appModule = module {
    single { DefaultCurrentActivityListener() }
    single { ActivityRetriever(get()) }
}