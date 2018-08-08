package com.lychee.ui.main.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.view.ViewGroup
import com.lychee.ui.main.page.home.HomeFragment
import com.lychee.ui.main.page.map.MapFragment
import com.lychee.ui.main.page.record.RecordFragment
import com.lychee.ui.main.page.setting.SettingFragment

class MainViewPagerAdapter(fragmentManager: FragmentManager): FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> HomeFragment.newInstance()
            1 -> RecordFragment.newInstance()
            2 -> MapFragment.newInstance()
            3 -> SettingFragment.newInstance()
            else -> throw IllegalArgumentException("unexpected error")
        }
    }

    override fun getCount(): Int = 4

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        /* super.destroyItem(container, position, `object`) */
    }
}