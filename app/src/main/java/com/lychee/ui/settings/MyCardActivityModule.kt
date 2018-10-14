package com.lychee.ui.settings

import android.arch.lifecycle.ViewModel
import com.lychee.di.qualifier.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MyCardActivityModule {

    @Binds
    @IntoMap
    @ViewModelKey(MyCardViewModel::class)
    abstract fun bindMyCardViewModel(myCardViewModel: MyCardViewModel): ViewModel
}