package com.lychee.di.study

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Inject

class UserActivityModule {

    @Provides fun provideUserToken() : String = "TOKEN"
}

interface UserActivityComponent {

    fun inject(userActivity : UserActivity)
}

class UserActivity {

    @Inject lateinit var retrofit: Retrofit

    fun a() {
        retrofit
    }
}

class UserRepository constructor(
        private val webAPI: WebAPI,
        private val userDatabase: UserDatabase
) {

    fun updateUser() {
        // DO UPDATE
    }
}

class WebAPI {

}

class UserDatabase {
}

class UserController {

    private val userRepository: UserRepository

    constructor() {
        userRepository = UserRepository(WebAPI(), UserDatabase())
    }

    fun syncData() {
        userRepository.updateUser()
    }
}