package com.lychee.ui.base

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.WindowManager
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject


/**
 * Status Bar Codes from
 * https://learnpainless.com/android/material/make-fully-android-transparent-status-bar
 */
abstract class BaseActivity<DataBindingType: ViewDataBinding, ViewModelType: BaseViewModel>
    : AppCompatActivity(), HasSupportFragmentInjector {

    abstract val layoutResId: Int

    lateinit var mBinding: DataBindingType

    @Inject
    lateinit var viewModelFactory : ViewModelProvider.Factory

    lateinit var mViewModel : ViewModelType

    abstract val viewModelClass : Class<ViewModelType>

    @Inject
    lateinit var supportFragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, layoutResId)
        mBinding.setLifecycleOwner(this)

        mViewModel = ViewModelProviders.of(this, viewModelFactory).get(viewModelClass)

        lifecycle.addObserver(mViewModel)

        if(/*Build.VERSION.SDK_INT >= 19 && */Build.VERSION.SDK_INT < 21) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true)
        }
        /* if(Build.VERSION.SDK_INT >= 19) */
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
            window.statusBarColor = Color.TRANSPARENT
        }

        if(Build.VERSION.SDK_INT >= 23) {

        }
    }

    private fun setWindowFlag(bits: Int, on: Boolean) {
        val win = window
        val winParams = win.attributes
        if (on) {
            winParams.flags = winParams.flags or bits
        } else {
            winParams.flags = winParams.flags and bits.inv()
        }
        win.attributes = winParams
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = supportFragmentInjector
}