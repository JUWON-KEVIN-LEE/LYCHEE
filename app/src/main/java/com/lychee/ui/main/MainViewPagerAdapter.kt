package com.lychee.ui.main

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.lychee.ui.main.PageInfo.POSITION_HOME
import com.lychee.ui.main.PageInfo.POSITION_MAP
import com.lychee.ui.main.PageInfo.POSITION_RECORD
import com.lychee.ui.main.PageInfo.POSITION_SETTING
import com.lychee.ui.main.PageInfo.POSITION_STATISTIC
import com.lychee.ui.main.home.HomeFragment
import com.lychee.ui.main.map.MapFragment
import com.lychee.ui.main.record.RecordFragment
import com.lychee.ui.main.setting.SettingFragment
import com.lychee.ui.main.statistic.StatisticFragment

class MainViewPagerAdapter constructor(fm : FragmentManager): FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment = when(position) {
        POSITION_HOME -> HomeFragment()
        POSITION_RECORD -> RecordFragment()
        POSITION_MAP -> MapFragment()
        POSITION_STATISTIC -> StatisticFragment()
        POSITION_SETTING -> SettingFragment()
        else -> throw IllegalArgumentException("View Pager Item Selected Position Error")
    }


    override fun getCount(): Int = PageInfo.COUNT
}