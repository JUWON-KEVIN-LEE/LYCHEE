package com.lychee.ui.base

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.WindowManager
import com.lychee.R
import dagger.android.AndroidInjection
import javax.inject.Inject


/**
 * Status Bar Codes from
 * https://learnpainless.com/android/material/make-fully-android-transparent-status-bar
 */
abstract class BaseActivity<DataBindingType: ViewDataBinding, ViewModelType: BaseViewModel>
    : AppCompatActivity() {

    abstract val layoutResId: Int

    lateinit var mBinding: DataBindingType

    @Inject
    lateinit var viewModelFactory : ViewModelProvider.Factory

    lateinit var mViewModel : ViewModelType

    abstract val viewModelClass : Class<ViewModelType>

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

            if(Build.VERSION.SDK_INT >= 23) {
                window.statusBarColor = Color.TRANSPARENT
                window.decorView.systemUiVisibility = window.decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            } else {
                window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimaryDark)
            }
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
}