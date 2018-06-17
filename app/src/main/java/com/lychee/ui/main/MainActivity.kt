package com.lychee.ui.main

import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.view.View
import android.widget.Toast
import com.lychee.R
import com.lychee.extensions.*
import com.lychee.ui.base.BaseActivity
import com.lychee.ui.calendar.CalendarFragment
import com.lychee.ui.main.PageInfo.POSITION_HOME
import com.lychee.ui.main.PageInfo.POSITION_MAP
import com.lychee.ui.main.PageInfo.POSITION_RECORD
import com.lychee.ui.main.PageInfo.POSITION_SETTING
import com.lychee.ui.main.PageInfo.POSITION_STATISTIC
import com.lychee.ui.main.home.HomeFragment
import kotlinx.android.synthetic.main.activity_main.*

/**
 * TODO
 * Config Change Handling
 */
class MainActivity : BaseActivity<MainViewModel>(R.layout.activity_main),
        HomeFragment.InteractionListener, ActionBarProvider {

    private var backPressedTime : Long = 0L

    override val viewModelClass: Class<MainViewModel>
        get() = MainViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        // DI, SET CONTENT VIEW, BIND VIEW MODEL
        super.onCreate(savedInstanceState)

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

        bottom_navigation_view.apply {
            disableShiftMode() // delete shifting animation

            setOnNavigationItemSelectedListener {
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
                    }
                }
            }
        }
    }

    // HOME CALENDAR
    override fun openCalendar() : Unit = container.run { visible(); replaceFragment(supportFragmentManager, CalendarFragment()) }

    private fun closeCalendar() : Unit = container.run { gone(); removeFragment(supportFragmentManager, CalendarFragment()) }

    // BACK KEY
    override fun onBackPressed()
        = container.takeIf { it.visibility == View.VISIBLE }
                  ?.let { closeCalendar() }
                  ?:backPressedTime
                        .takeIf { System.currentTimeMillis() - it < SHORT_DURATION_TIMEOUT }
                        ?.let { super.onBackPressed() }
                        ?:run { onBackPressedFeedback(EXIT_MESSAGE); backPressedTime = System.currentTimeMillis() }
    // EXIT MEASSAGE
    private fun onBackPressedFeedback(message : String)
        = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

    // ACTION BAR
    override fun setActionBarColor(color: Int) {
        supportActionBar?.setBackgroundDrawable(ColorDrawable(color))
    }

    companion object {
        fun start(context : Context) = context.startActivity(Intent(context, MainActivity::class.java))

        // COST : RES > STATIC
        const val EXIT_MESSAGE = "한번 더 누르시면 종료됩니다."
        const val SHORT_DURATION_TIMEOUT = 2000L
    }
}