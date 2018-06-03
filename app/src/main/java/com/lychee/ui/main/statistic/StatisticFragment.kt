package com.lychee.ui.main.statistic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lychee.R
import com.lychee.ui.base.BaseFragment

class StatisticFragment : BaseFragment<StatisticViewModel>() {

    override val viewModelClass: Class<StatisticViewModel>
        get() = StatisticViewModel::class.java

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
        = inflater.inflate(R.layout.fragment_statistic, container, false)

}