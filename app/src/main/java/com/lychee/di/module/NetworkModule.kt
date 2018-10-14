package com.lychee.di.module

import android.content.Context
import com.lychee.data.weather.remote.WeatherApi
import com.lychee.di.qualifier.ApplicationLevel
import com.lychee.di.scope.ApplicationScoped
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

@Module
class NetworkModule {

    @Provides @ApplicationScoped
    fun provideInterceptor() : Interceptor
            = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

    @Provides @ApplicationScoped
    fun provideCache(context: Context) : Cache
            = Cache(context.cacheDir, 10 * 10 * 1024L)

    @Provides @ApplicationScoped
    fun provideOkHttpClient(interceptor: Interceptor, cache : Cache) : OkHttpClient
            = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(30L, TimeUnit.SECONDS)
            .writeTimeout(15L, TimeUnit.SECONDS)
            .readTimeout(15L, TimeUnit.SECONDS)
            .cache(cache)
            .build()

    @Provides @ApplicationScoped
    fun provideGsonConverterFactory() : GsonConverterFactory
            = GsonConverterFactory.create()

    @Provides @ApplicationScoped
    fun provideRxJava2CallAdapterFactory() : RxJava2CallAdapterFactory
            = RxJava2CallAdapterFactory.create()

    @Provides @ApplicationScoped
    fun provideRetrofit(okHttpClient: OkHttpClient,
                        gsonConverterFactory: GsonConverterFactory,
                        rxJava2CallAdapterFactory: RxJava2CallAdapterFactory) : Retrofit
            = Retrofit.Builder()
            .addConverterFactory(gsonConverterFactory)
            .addCallAdapterFactory(rxJava2CallAdapterFactory)
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()

    @Provides @ApplicationScoped
    fun provideWeahterApi(retrofit: Retrofit): WeatherApi = retrofit.create(WeatherApi::class.java)

    companion object {
        const val BASE_URL = "https://api.openweathermap.org"
    }
}