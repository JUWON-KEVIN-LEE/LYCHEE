package com.lychee.ui.splash

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.lychee.R
import com.lychee.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        val title = getString(R.string.app_name)
        val content = getString(R.string.app_splash)

        Handler().apply {
            postDelayed({
                animateTextTyping(text = title, textView = lychee_title, delay = 100)
                animateTextTyping(text = content, textView = lychee_content, delay = 100)
            }, 500)

            postDelayed({ MainActivity.start(this@SplashActivity); finish() }, 2500)
        }
    }

    private fun animateTextTyping(text : String, textView : TextView, delay : Long) {
        Handler().apply {
            for(i in 0 until text.length) {
                postDelayed({ textView.text = text.subSequence(0, i+1) }, delay*((i+1).toLong()))
            }
        }
    }
}
