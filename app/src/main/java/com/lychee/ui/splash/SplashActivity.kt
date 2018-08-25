package com.lychee.ui.splash

import android.Manifest
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.WindowManager
import com.lychee.R
import com.lychee.ui.main.MainActivity
import pub.devrel.easypermissions.EasyPermissions

class SplashActivity:
        AppCompatActivity(),
        EasyPermissions.PermissionCallbacks
{

    private val permissions = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_SMS,
            Manifest.permission.RECEIVE_SMS)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        if(/*Build.VERSION.SDK_INT >= 19 && */Build.VERSION.SDK_INT < 21) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true)
        }
        /* if(Build.VERSION.SDK_INT >= 19) */
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
            window.statusBarColor = Color.TRANSPARENT
        }

        if(Build.VERSION.SDK_INT >= 23) {
            window.decorView.systemUiVisibility = window.decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }

    private fun setWindowFlag(bits: Int, on: Boolean) {
        val win = window
        val winParams = win.attributes
        if (on) {
            winParams.flags = winParams.flags or bits
        } else {
            winParams.flags = winParams.flags and bits.inv()
        }
        win.attributes = winParams
    }

    override fun onResume() {
        super.onResume()

        // Refactor Logic TODO
        if(EasyPermissions.hasPermissions(this, *permissions)) {
            startMainActivityDelayed()
        } else {
            EasyPermissions.requestPermissions(
                    this,
                    "권한이 거부되면 일부 기능을 사용하실수 없습니다.",
                    REQUEST_CODE_LOCATION,
                    *permissions)
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        startMainActivityDelayed()
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    private fun startMainActivityDelayed() {
        Handler().postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 500L)
    }

    companion object {
        private const val REQUEST_CODE_LOCATION = 10
    }
}
