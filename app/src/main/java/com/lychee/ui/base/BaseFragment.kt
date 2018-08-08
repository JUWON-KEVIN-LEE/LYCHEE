package com.lychee.ui.base

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lychee.di.qualifier.FragmentLevel
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

abstract class BaseFragment<DataBindingType: ViewDataBinding, ViewModelType : BaseViewModel>: Fragment() {

    abstract val layoutResId: Int

    @Inject @field:FragmentLevel
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var mViewModel: ViewModelType

    abstract val viewModelClass: Class<ViewModelType>

    lateinit var mBinding: DataBindingType

    lateinit var mContext: Context

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        mBinding = DataBindingUtil.inflate(inflater, layoutResId, container, false)
        mBinding.setLifecycleOwner(this)

        onCreateView(savedInstanceState)

        return mBinding.root
    }

    abstract fun onCreateView(savedInstanceState: Bundle?)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mViewModel = ViewModelProviders.of(this, viewModelFactory).get(viewModelClass)

        lifecycle.addObserver(mViewModel)
    }

}