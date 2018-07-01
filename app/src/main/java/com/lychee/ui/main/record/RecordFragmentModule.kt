package com.lychee.ui.main.record

import android.arch.lifecycle.ViewModel
import com.lychee.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class RecordFragmentModule {

    @Binds
    @IntoMap
    @ViewModelKey(RecordViewModel::class)
    abstract fun bindRecordViewModel(recordViewModel: RecordViewModel) : ViewModel
}