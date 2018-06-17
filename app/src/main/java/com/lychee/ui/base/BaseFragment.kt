package com.lychee.ui.base

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.support.DaggerFragment
import javax.inject.Inject

abstract class BaseFragment<D: ViewDataBinding, V : BaseViewModel> constructor(
        private val resId : Int
) : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory : ViewModelProvider.Factory

    open lateinit var viewModel : V

    abstract val viewModelClass : Class<V>

    open lateinit var binding : D

    open lateinit var mContext : Context

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        context?.let { mContext = it }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, resId, container, false)

        onCreateView()

        return binding.root
    }

    abstract fun onCreateView()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(viewModelClass)

        lifecycle.addObserver(viewModel)
        binding.setLifecycleOwner(this)
    }

}