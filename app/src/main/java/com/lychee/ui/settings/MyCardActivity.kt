package com.lychee.ui.settings

import android.os.Bundle
import com.lychee.R
import com.lychee.databinding.ActivityMyCardBinding
import com.lychee.ui.base.ui.BaseActivity
import kotlin.math.roundToInt

class MyCardActivity : BaseActivity<ActivityMyCardBinding, MyCardViewModel>() {

    override val layoutResId: Int
        get() = R.layout.activity_my_card

    override val viewModelClass: Class<MyCardViewModel>
        get() = MyCardViewModel::class.java

    private val density by lazy { resources.displayMetrics.density }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init()
    }

    private fun init() {
        mBinding.myCardBackButton.setOnClickListener { finish() }

        with(mBinding.myCardViewPager) {
            clipToPadding = false

            val pageMargin = (24 * density).roundToInt()
            val partialWidth = ((36 + 24) * density).roundToInt()
            val topBottomPadding = (16 * density).roundToInt()

            this.pageMargin = pageMargin
            setPadding(partialWidth, topBottomPadding, partialWidth, topBottomPadding)

            setPageTransformer(false, MyCardPagerTransformer())

            adapter = MyCardPagerAdapter(this@MyCardActivity)
        }
    }
}
