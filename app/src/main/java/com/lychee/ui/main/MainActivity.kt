package com.lychee.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.view.ViewPager
import com.lychee.R
import com.lychee.ui.base.BaseActivity
import com.lychee.ui.main.PageInfo.POSITION_HOME
import com.lychee.ui.main.PageInfo.POSITION_MAP
import com.lychee.ui.main.PageInfo.POSITION_RECORD
import com.lychee.ui.main.PageInfo.POSITION_SETTING
import com.lychee.ui.main.PageInfo.POSITION_STATISTIC
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<MainViewModel>() {

    override val viewModelClass: Class<MainViewModel>
        get() = MainViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        view_pager.apply {
            adapter = MainViewPagerAdapter(supportFragmentManager)

            addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrollStateChanged(state: Int) {}
                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
                override fun onPageSelected(position: Int) {
                    when(position) {
                        POSITION_HOME -> { bottom_navigation_view.selectedItemId = R.id.action_home }
                        POSITION_RECORD -> { bottom_navigation_view.selectedItemId = R.id.action_record }
                        POSITION_MAP -> { bottom_navigation_view.selectedItemId = R.id.action_map }
                        POSITION_STATISTIC -> { bottom_navigation_view.selectedItemId = R.id.action_statistic }
                        POSITION_SETTING -> { bottom_navigation_view.selectedItemId = R.id.action_setting }
                    }
                }
            })
        }

        bottom_navigation_view.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.action_home -> {
                    view_pager.currentItem = POSITION_HOME
                    true
                }
                R.id.action_record -> {
                    view_pager.currentItem = POSITION_RECORD
                    true
                }
                R.id.action_map -> {
                    view_pager.currentItem = POSITION_MAP
                    true
                }
                R.id.action_statistic -> {
                    view_pager.currentItem = POSITION_STATISTIC
                    true
                }
                R.id.action_setting -> {
                    view_pager.currentItem = POSITION_SETTING
                    true
                }
                else -> {
                    throw IllegalArgumentException("Bottom Navigation View Item Selected Position Error")
                    false
                }
            }
        }
    }

    companion object {
        fun start(context : Context) = context.startActivity(Intent(context, MainActivity::class.java))
    }
}