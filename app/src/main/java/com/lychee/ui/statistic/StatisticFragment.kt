package com.lychee.ui.statistic

import android.os.Bundle
import android.view.ViewGroup
import com.lychee.R
import com.lychee.databinding.FragmentStatisticBinding
import com.lychee.ui.base.ui.BaseFragment

class StatisticFragment:
        BaseFragment<FragmentStatisticBinding, StatisticViewModel>()
{

    override val layoutResId: Int
        get() = R.layout.fragment_statistic

    override val viewModelClass: Class<StatisticViewModel>
        get() = StatisticViewModel::class.java

    override fun FragmentStatisticBinding.onCreateView(
            container: ViewGroup?,
            savedInstanceState: Bundle?)
    {

    }
}