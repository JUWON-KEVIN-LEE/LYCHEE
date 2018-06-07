package com.lychee.ui.base

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.DaggerAppCompatActivity
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

/**
 * TODO
 * Data Binding
 */
abstract class BaseActivity<V : BaseViewModel> constructor(
        private val layoutResId : Int
) : DaggerAppCompatActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var viewModelFactory : ViewModelProvider.Factory
    open lateinit var viewModel : V

    abstract val viewModelClass : Class<V>

    @Inject
    lateinit var supportFragmentInjector : DispatchingAndroidInjector<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutResId)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(viewModelClass)

        lifecycle.addObserver(viewModel)
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = supportFragmentInjector
}