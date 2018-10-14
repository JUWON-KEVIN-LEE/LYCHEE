package com.lychee.ui.base.ui

import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lychee.BR
import com.lychee.ui.base.viewmodel.BaseViewModel
import com.lychee.util.extensions.getViewModel
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

abstract class BaseFragment<DataBindingType: ViewDataBinding, ViewModelType : BaseViewModel>: Fragment() {

    abstract val layoutResId: Int

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    open lateinit var mViewModel: ViewModelType

    abstract val viewModelClass: Class<ViewModelType>

    lateinit var mBinding: DataBindingType

    private val bindingVariable: Int = BR.viewModel

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mViewModel = getViewModel(viewModelFactory, viewModelClass)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        mBinding = DataBindingUtil.inflate(inflater, layoutResId, container, false)
        mBinding.setLifecycleOwner(this)

        mBinding.onCreateView(container, savedInstanceState)

        return mBinding.root
    }

    abstract fun DataBindingType.onCreateView(container: ViewGroup?, savedInstanceState: Bundle?)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.setVariable(bindingVariable, mViewModel)
        mBinding.executePendingBindings()

        lifecycle.addObserver(mViewModel)
    }
}