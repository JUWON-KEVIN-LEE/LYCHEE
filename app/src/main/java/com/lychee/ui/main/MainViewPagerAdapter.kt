package com.lychee.ui.main

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.view.ViewGroup
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

    private val cache : MutableMap<Int, Fragment> = mutableMapOf()

    override fun getItem(position: Int): Fragment = when(position) {
        POSITION_HOME -> {
            cache[POSITION_HOME]
                    ?:let { HomeFragment().also { onCache(POSITION_HOME, it) } }
        }
        POSITION_RECORD -> {
            cache[POSITION_RECORD]
                    ?:let { RecordFragment().also { onCache(POSITION_RECORD, it) } }
        }
        POSITION_MAP -> {
            cache[POSITION_MAP]
                    ?:let { MapFragment().also { onCache(POSITION_MAP, it) } }
        }
        POSITION_STATISTIC -> {
            cache[POSITION_STATISTIC]
                    ?:let { StatisticFragment().also { onCache(POSITION_STATISTIC, it) } }
        }
        POSITION_SETTING -> {
            cache[POSITION_SETTING]
                    ?:let { SettingFragment().also { onCache(POSITION_SETTING, it) } }
        }
        else -> throw IllegalArgumentException("Unused exception")
    }

    override fun getCount(): Int = PageInfo.COUNT

    private fun onCache(position: Int, fragment: Fragment) { cache[position] = fragment }

    // block destroy fragments
    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) { /* super.destroyItem(container, position, `object`) */ }
}