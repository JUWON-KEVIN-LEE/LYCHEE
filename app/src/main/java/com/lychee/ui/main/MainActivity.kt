package com.lychee.ui.main

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.view.MenuItem
import android.widget.Toast
import com.lychee.R
import com.lychee.databinding.ActivityMainBinding
import com.lychee.ui.add.AddActivity
import com.lychee.ui.base.ui.BaseActivity
import com.lychee.ui.home.HomeFragment
import com.lychee.ui.map.MapFragment
import com.lychee.ui.record.RecordFragment
import com.lychee.ui.setting.SettingFragment
import com.lychee.util.extensions.consume
import com.lychee.util.extensions.gone
import com.lychee.util.extensions.visible
import com.lychee.widget.disableShiftMode
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

/**
 * TODO
 * Config Change Handling
 */
class MainActivity:
        BaseActivity<ActivityMainBinding, MainViewModel>(),
        HasSupportFragmentInjector
{

    @Inject
    lateinit var supportFragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = supportFragmentInjector

    override val layoutResId: Int
        get() = R.layout.activity_main

    override val viewModelClass: Class<MainViewModel>
        get() = MainViewModel::class.java

    private var prevMenuItem: MenuItem? = null

    private var prevBackButtonPressedTime: Long = 0L

    private var mNavigationFragment: NavigationFragment? = null

    @Inject lateinit var mSharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()

        savedInstanceState
                ?.let {
                    mNavigationFragment =
                            supportFragmentManager.findFragmentById(FRAGMENT_CONTAINER_ID) as? NavigationFragment
                            ?: throw IllegalArgumentException("Activity Recreated. But Fragment Not Found.")
                }
                ?:let { mBinding.mainBottomNavigationView.selectedItemId = R.id.action_menu_home }
    }

    private fun initView() {
        with(mBinding) {
            mainBottomNavigationView.disableShiftMode()

            mainBottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
                when(menuItem.itemId) {
                    R.id.action_menu_home -> consume {
                        mainBottomNavigationView.selectBottomNavigationMenuItem(0)

                        mainFloatingButton.visible()

                        hideCurrentShowingFragment()

                        val fragment = supportFragmentManager.findFragmentByTag(HomeFragment.TAG)

                        if(fragment != null) {
                            showFragment(fragment)
                        } else {
                            addFragment(
                                    HomeFragment.newInstance().apply { mNavigationFragment = this },
                                    HomeFragment.TAG
                            )
                        }
                    }

                    R.id.action_menu_record -> consume {
                        mainBottomNavigationView.selectBottomNavigationMenuItem(1)

                        mainFloatingButton.visible()

                        hideCurrentShowingFragment()

                        val fragment = supportFragmentManager.findFragmentByTag(RecordFragment.TAG)

                        if(fragment != null) {
                            showFragment(fragment)
                        } else {
                            addFragment(
                                    RecordFragment.newInstance().apply { mNavigationFragment = this },
                                    RecordFragment.TAG
                            )
                        }
                    }

                    R.id.action_menu_map -> consume {
                        mainBottomNavigationView.selectBottomNavigationMenuItem(2)

                        mainFloatingButton.gone()

                        hideCurrentShowingFragment()

                        val fragment = supportFragmentManager.findFragmentByTag(MapFragment.TAG)

                        if(fragment != null) {
                            showFragment(fragment)
                        } else {
                            addFragment(
                                    MapFragment.newInstance().apply { mNavigationFragment = this },
                                    MapFragment.TAG
                            )
                        }
                    }

                    R.id.action_menu_setting -> consume {
                        mainBottomNavigationView.selectBottomNavigationMenuItem(3)

                        mainFloatingButton.gone()

                        hideCurrentShowingFragment()

                        val fragment = supportFragmentManager.findFragmentByTag(SettingFragment.TAG)

                        if(fragment != null) {
                            showFragment(fragment)
                        } else {
                            addFragment(
                                    SettingFragment.newInstance().apply { mNavigationFragment = this },
                                    SettingFragment.TAG
                            )
                        }
                    }

                    else -> false
                }
            }

            mainFloatingButton.setOnClickListener {
                val intent = Intent(this@MainActivity, AddActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun hideCurrentShowingFragment() {
        (mNavigationFragment as? Fragment)
                ?.let { currentFragment ->
                    supportFragmentManager
                            .beginTransaction()
                            .hide(currentFragment)
                            .commitNow()
                }
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager
                .beginTransaction()
                .disallowAddToBackStack()
                .show(fragment)
                .commitNow()

        mNavigationFragment =
                fragment as? NavigationFragment
                ?: throw IllegalArgumentException("Not a Navigation Fragment")
    }

    private fun addFragment(fragment: Fragment, tag: String) {
        supportFragmentManager
                .beginTransaction()
                .disallowAddToBackStack()
                .add(FRAGMENT_CONTAINER_ID, fragment, tag)
                .commitNow()
    }

    private fun BottomNavigationView.selectBottomNavigationMenuItem(position: Int) {
        prevMenuItem
                ?.setChecked(false)
                ?:let { menu.getItem(0).isChecked = false }

        menu.getItem(position).isChecked = true
        prevMenuItem = menu.getItem(position)
    }

    override fun onBackPressed() {
        if(mNavigationFragment?.onBackPressed() == false) {
            handleBackPressed()
        }
    }

    private fun handleBackPressed() {
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

    fun navigateToMapPage() {

    }

    companion object {
        private const val FRAGMENT_CONTAINER_ID = R.id.mainFragmentContainer

        const val EXIT_MESSAGE = "한번 더 누르시면 종료됩니다."
        const val EXIT_TIMEOUT = 2000L
    }
}