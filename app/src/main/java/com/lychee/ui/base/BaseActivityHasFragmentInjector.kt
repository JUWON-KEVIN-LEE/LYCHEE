package com.lychee.ui.base

import android.databinding.ViewDataBinding
import android.support.v4.app.Fragment
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

abstract class BaseActivityHasFragmentInjector<DataBindingType: ViewDataBinding, ViewModelType: BaseViewModel>:
        BaseActivity<DataBindingType, ViewModelType>(),
        HasSupportFragmentInjector
{

    @Inject
    lateinit var supportFragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = supportFragmentInjector
}