package com.lychee.ui.main.statistic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lychee.R
import com.lychee.databinding.FragmentStatisticBinding
import com.lychee.ui.base.BaseFragment

class StatisticFragment : BaseFragment<FragmentStatisticBinding, StatisticViewModel>(R.layout.fragment_statistic) {

    override val viewModelClass: Class<StatisticViewModel>
        get() = StatisticViewModel::class.java

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View
        = super.onCreateView(inflater, container, savedInstanceState)

    override fun init() {

    }
}