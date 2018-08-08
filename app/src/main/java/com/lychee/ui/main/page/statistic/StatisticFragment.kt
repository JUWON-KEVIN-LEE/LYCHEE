package com.lychee.ui.main.page.statistic

import android.os.Bundle
import com.lychee.R
import com.lychee.databinding.FragmentStatisticBinding
import com.lychee.ui.base.BaseFragment

class StatisticFragment : BaseFragment<FragmentStatisticBinding, StatisticViewModel>() {

    override val layoutResId: Int
        get() = R.layout.fragment_statistic

    override val viewModelClass: Class<StatisticViewModel>
        get() = StatisticViewModel::class.java

    override fun onCreateView(savedInstanceState: Bundle?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}