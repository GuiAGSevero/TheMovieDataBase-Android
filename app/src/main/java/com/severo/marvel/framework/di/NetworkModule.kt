package com.severo.marvel.framework.di

import com.severo.marvel.BuildConfig
import com.severo.marvel.framework.network.MarvelApi
import com.severo.marvel.framework.network.interceptor.AuthorizationInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Calendar
import java.util.TimeZone
import java.util.concurrent.TimeUnit

private const val DEFAULT_TIMEOUT_SECONDS = 15L

val networkModule = module {

    single {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.NONE
        }
    }

    single {
        AuthorizationInterceptor(
            publicKey = BuildConfig.PUBLIC_KEY,
            privateKey = BuildConfig.PRIVATE_KEY,
            calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        )
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .addInterceptor(get<AuthorizationInterceptor>())
            .readTimeout(DEFAULT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .connectTimeout(DEFAULT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .build()
    }

    single {
        GsonConverterFactory.create()
    }

    single<MarvelApi> {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(get())
            .addConverterFactory(get<GsonConverterFactory>())
            .build()
            .create(MarvelApi::class.java)
    }
}