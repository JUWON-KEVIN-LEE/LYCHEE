package com.lychee.di.module

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.lychee.di.ViewModelKey
import com.lychee.ui.cardlist.CardListViewModel
import com.lychee.ui.viewmodel.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory) : ViewModelProvider.Factory

    // CARD LIST ACTIVITY
    @Binds
    @IntoMap
    @ViewModelKey(CardListViewModel::class)
    abstract fun bindCardListViewModel(cardListViewModel: CardListViewModel) : ViewModel
}