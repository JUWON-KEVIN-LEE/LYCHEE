package com.lychee.ui.splash

import android.Manifest
import android.content.SharedPreferences
import android.os.Bundle
import com.lychee.R
import com.lychee.databinding.ActivitySplashBinding
import com.lychee.ui.base.ui.BaseActivity
import com.lychee.util.extensions.gone
import pub.devrel.easypermissions.EasyPermissions
import javax.inject.Inject

class SplashActivity:
        BaseActivity<ActivitySplashBinding, SplashViewModel>(),
        EasyPermissions.PermissionCallbacks
{

    override val layoutResId: Int
        get() = R.layout.activity_splash

    override val viewModelClass: Class<SplashViewModel>
        get() = SplashViewModel::class.java

    private val permissions = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_SMS,
            Manifest.permission.RECEIVE_SMS)

    @Inject lateinit var mSharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding.splashIntroLayout.gone()

        mBinding.splashStartButton.setOnClickListener { finish() }
    }

    override fun onResume() {
        super.onResume()

        if(EasyPermissions.hasPermissions(this, *permissions)) {
            /* Unreachable */
            with(mSharedPreferences.edit()) {
                putBoolean("permission", true)
                apply()
            }

            finish()
        } else {
            EasyPermissions.requestPermissions(
                    this,
                    "권한이 거부되면 일부 기능을 사용하실수 없습니다.",
                    REQUEST_CODE_LOCATION,
                    *permissions)
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        with(mSharedPreferences.edit()) {
            putBoolean("permission", true)
            apply()
        }

        finish()
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        with(mSharedPreferences.edit()) {
            putBoolean("permission", false)
            apply()
        }

        finish()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    companion object {
        private const val REQUEST_CODE_LOCATION = 10
    }
}
