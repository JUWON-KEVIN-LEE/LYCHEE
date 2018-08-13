package com.lychee.di.module

import android.content.Context
import com.lychee.di.scope.ApplicationScope
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

    @Provides @ApplicationScope
    fun provideInterceptor() : Interceptor
            = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

    @Provides @ApplicationScope
    fun provideCache(context: Context) : Cache
            = Cache(context.cacheDir, 10 * 10 * 1024L)

    @Provides @ApplicationScope
    fun provideOkHttpClient(interceptor: Interceptor, cache : Cache) : OkHttpClient
            = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(30L, TimeUnit.SECONDS)
            .writeTimeout(15L, TimeUnit.SECONDS)
            .readTimeout(15L, TimeUnit.SECONDS)
            .cache(cache)
            .build()

    @Provides @ApplicationScope
    fun provideGsonConverterFactory() : GsonConverterFactory
            = GsonConverterFactory.create()

    @Provides @ApplicationScope
    fun provideRxJava2CallAdapterFactory() : RxJava2CallAdapterFactory
            = RxJava2CallAdapterFactory.create()

    @Provides @ApplicationScope
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