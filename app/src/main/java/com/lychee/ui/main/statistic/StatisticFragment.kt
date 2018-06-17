package com.lychee.ui.main.statistic

import com.lychee.R
import com.lychee.databinding.FragmentStatisticBinding
import com.lychee.ui.base.BaseFragment

class StatisticFragment : BaseFragment<FragmentStatisticBinding, StatisticViewModel>(R.layout.fragment_statistic) {

    override val viewModelClass: Class<StatisticViewModel>
        get() = StatisticViewModel::class.java

    override fun onCreateView() {

    }
}