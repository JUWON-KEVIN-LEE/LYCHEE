package com.lychee.di.module

import android.app.Application
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideInterceptor() : Interceptor
            = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

    @Provides
    @Singleton
    fun provideCache(app : Application) : Cache
            = Cache(app.cacheDir, 10 * 10 * 1024L)

    @Provides
    @Singleton
    fun provideOkhttpClient(interceptor: Interceptor, cache : Cache) : OkHttpClient
            = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(30L, TimeUnit.SECONDS)
            .writeTimeout(15L, TimeUnit.SECONDS)
            .readTimeout(15L, TimeUnit.SECONDS)
            .cache(cache)
            .build()

    @Provides
    @Singleton
    fun provideGsonConverterFactory() : GsonConverterFactory
            = GsonConverterFactory.create()

    @Provides
    @Singleton
    fun provideRxJava2CallAdapterFactory() : RxJava2CallAdapterFactory
            = RxJava2CallAdapterFactory.create()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient,
                        gsonConverterFactory: GsonConverterFactory,
                        rxJava2CallAdapterFactory: RxJava2CallAdapterFactory) : Retrofit
            = Retrofit.Builder()
            .addConverterFactory(gsonConverterFactory)
            .addCallAdapterFactory(rxJava2CallAdapterFactory)
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()

    companion object {
        const val BASE_URL = ""
    }
}