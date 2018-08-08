package com.lychee.di.module

import dagger.Module
import dagger.Provides
import io.realm.Realm
import io.realm.RealmConfiguration
import javax.inject.Singleton

@Module
class DatabaseModule {

    // TODO 언제 세팅해주는게 좋을지 고민
    @Provides @Singleton
    fun provideRealmConfiguration() : RealmConfiguration
        = RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build().apply { Realm.setDefaultConfiguration(this) }
}