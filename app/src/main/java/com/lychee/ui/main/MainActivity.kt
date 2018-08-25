package com.lychee.ui.main

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.lychee.R
import com.lychee.databinding.ActivityMainBinding
import com.lychee.ui.base.BaseActivity
import com.lychee.ui.main.adapter.MainViewPagerAdapter
import com.lychee.util.extensions.gone
import com.lychee.util.extensions.visible
import com.lychee.view.main.disableShiftMode

/**
 * TODO
 * Config Change Handling
 */
class MainActivity:
        BaseActivity<ActivityMainBinding, MainViewModel>(),
        PageOnClickListener
{

    override val layoutResId: Int
        get() = R.layout.activity_main

    override val viewModelClass: Class<MainViewModel>
        get() = MainViewModel::class.java

    private var prevMenuItem: MenuItem? = null

    private var prevBackButtonPressedTime: Long = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
    }

    private fun initView() {
        with(mBinding) {
            mainBottomNavigationView.disableShiftMode()

            mainBottomNavigationView.setOnNavigationItemSelectedListener {menuItem ->
                when(menuItem.itemId) {
                    R.id.action_menu_home -> {
                        mainViewPager.currentItem = 0
                        true
                    }
                    R.id.action_menu_record -> {
                        mainViewPager.currentItem = 1
                        true
                    }
                    R.id.action_menu_map -> {
                        mainViewPager.currentItem = 2
                        true
                    }
                    R.id.action_menu_setting -> {
                        mainViewPager.currentItem = 3
                        true
                    }
                    else -> throw IllegalArgumentException("unexpected error")
                }
            }

            mainViewPager.addOnPageChangeListener(object: ViewPager.OnPageChangeListener {
                override fun onPageScrollStateChanged(state: Int) {
                    mainViewPager.lock = state == ViewPager.SCROLL_STATE_IDLE && mainViewPager.currentItem == 2
                }

                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

                override fun onPageSelected(position: Int) {
                    if(position > 1) { mBinding.mainFloatingButton.gone() }
                    else { mBinding.mainFloatingButton.visible() }

                    prevMenuItem
                            ?.setChecked(false)
                            ?:let { mainBottomNavigationView.menu.getItem(0).isChecked = false }

                    mainBottomNavigationView.menu.getItem(position).isChecked = true
                    prevMenuItem = mainBottomNavigationView.menu.getItem(position)
                }
            })
            mainViewPager.adapter = MainViewPagerAdapter(supportFragmentManager)

            mainFloatingButton
        }
    }

    /**
     * 페이지 내부 뷰의 클릭 이벤트를 수신
     */
    override fun onPageClick(view: View) {
        when(view.id) {

        }
    }

    override fun onBackPressed() {
        if(prevBackButtonPressedTime == 0L) {
            prevBackButtonPressedTime = System.currentTimeMillis()
            onBackPressedFeedback(EXIT_MESSAGE)
        } else {
            val now = System.currentTimeMillis()

            if(now - prevBackButtonPressedTime < EXIT_TIMEOUT) {
                super.onBackPressed()
            } else {
                prevBackButtonPressedTime = now
                onBackPressedFeedback(EXIT_MESSAGE)
            }
        }
    }

    private fun onBackPressedFeedback(message : String)
            = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

    companion object {
        const val EXIT_MESSAGE = "한번 더 누르시면 종료됩니다."
        const val EXIT_TIMEOUT = 2000L
    }
}