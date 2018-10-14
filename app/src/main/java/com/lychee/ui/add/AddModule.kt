package com.lychee.ui.add

import android.arch.lifecycle.ViewModel
import com.lychee.di.qualifier.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class AddModule {

    @Binds
    @IntoMap
    @ViewModelKey(AddViewModel::class)
    abstract fun bindAddViewModel(addViewModel: AddViewModel): ViewModel
}