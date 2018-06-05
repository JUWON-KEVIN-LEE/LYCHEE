package com.lychee.ui.splash

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import com.lychee.R
import com.lychee.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val t = "Lychee" // title
        val c = "똑똑한 금융생활, 리치머니와 함께" // content

        Handler().apply {
            postDelayed({
                lottie_view.visibility = View.GONE
                animateTextTyping(text = t, textView = lychee, delay = 150)
                animateTextTyping(text = c, textView = lychee_kor, delay = 150)
            }, 2000)
        }

        // TEST
        lychee.setOnClickListener {
            MainActivity.start(this@SplashActivity)
            finish()
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
