package com.playbowdogs.neighbors_dogsitter_android.di

import com.playbowdogs.neighbors_dogsitter_android.BuildConfig
import com.playbowdogs.neighbors_dogsitter_android.data.api.ApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single(named("BASE_ANGEL_CAM_URL")) {
        "https://api.angelcam.com/v1/"
    }

    single {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        interceptor
    }

    single {
        val okHttpClient = OkHttpClient().newBuilder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) {
            okHttpClient.addInterceptor(get<HttpLoggingInterceptor>())
        }
        okHttpClient.build()
    }

    single {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    single {
        Retrofit.Builder().baseUrl(get<String>(named("BASE_ANGEL_CAM_URL")))
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .client(get())
            .build()
    }

    single {
        get<Retrofit>().create(ApiService::class.java)
    }
}