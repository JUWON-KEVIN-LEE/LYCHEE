package com.lychee.ui.main

import android.os.Build
import android.os.Bundle
import android.support.transition.ChangeBounds
import android.support.transition.TransitionManager
import android.support.v4.view.ViewCompat
import android.support.v4.view.ViewPager
import android.support.v7.graphics.drawable.DrawerArrowDrawable
import android.view.MenuItem
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.Toast
import com.lychee.R
import com.lychee.databinding.ActivityMainBinding
import com.lychee.ui.base.BaseActivity
import com.lychee.ui.main.adapter.MainViewPagerAdapter
import com.lychee.util.extensions.dpToPx
import com.lychee.util.extensions.update
import com.lychee.view.disableShiftMode

/**
 * TODO
 * Config Change Handling
 */
class MainActivity:
        BaseActivity<ActivityMainBinding, MainViewModel>(),
        PageOnClickListener,
        PageInteractionListener
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
            if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) ViewCompat.setElevation(mainBottomNavigationView, 0f)

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
                override fun onPageScrollStateChanged(state: Int) {}

                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

                override fun onPageSelected(position: Int) {
                    prevMenuItem
                            ?.setChecked(false)
                            ?:let { mainBottomNavigationView.menu.getItem(0).isChecked = false }

                    mainBottomNavigationView.menu.getItem(position).isChecked = true
                    prevMenuItem = mainBottomNavigationView.menu.getItem(position)
                }
            })
            mainViewPager.adapter = MainViewPagerAdapter(supportFragmentManager)

            mainDrawerButton.setImageDrawable(DrawerArrowDrawable(this@MainActivity))
            mainDrawerButton.setOnClickListener{
                if(mainParentLayout.drawerArrowDrawableProgress == 0f) mainParentLayout.drawerArrowDrawableProgress = 1f
                else if(mainParentLayout.drawerArrowDrawableProgress == 1f) mainParentLayout.drawerArrowDrawableProgress = 0f
            }
        }
    }

    /**
     * 페이지 내부 뷰들과 액티비티 뷰들의 클릭 이벤트를 처리
     */
    override fun onClick(view: View) {
        when(view.id) {

            /*
            R.id.mainDrawerButton -> {
                val drawerArrowDrawable = mBinding.mainDrawerButton.drawable as DrawerArrowDrawable

                animateDrawer(drawerArrowDrawable)
            }
            */
        }
    }

    override fun showToolbarAndBottomNavigationView() {
        mBinding.mainParentLayout.update {
            setGuidelineBegin(R.id.mainTopGuideline, dpToPx(56))
            setGuidelineEnd(R.id.mainBottomGuideline, dpToPx(56))
        }

        TransitionManager.beginDelayedTransition(mBinding.mainParentLayout, ChangeBounds().apply {
            startDelay = 250L
            duration = 200L
            interpolator = LinearInterpolator()
        })
    }

    override fun hideToolbarAndBottomNavigationView() {
        mBinding.mainParentLayout.update {
            setGuidelineBegin(R.id.mainTopGuideline, 0)
            setGuidelineEnd(R.id.mainBottomGuideline, 0)
        }

        TransitionManager.beginDelayedTransition(mBinding.mainParentLayout, ChangeBounds().apply {
            startDelay = 250L
            duration = 200L
            interpolator = LinearInterpolator()
        })
    }

    override fun onScaleToolbarAndBottomNavigationView(toScale: Float) {

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