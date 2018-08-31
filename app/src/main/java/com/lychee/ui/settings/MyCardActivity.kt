package com.lychee.ui.settings

import android.os.Bundle
import com.lychee.R
import com.lychee.databinding.ActivityMyCardBinding
import com.lychee.ui.base.BaseActivity
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
        with(mBinding.myCardViewPager) {
            clipToPadding = false

            val pageMargin = (24 * density).roundToInt()
            val partialWidth = ((36 + 24) * density).roundToInt()

            this.pageMargin = pageMargin
            setPadding(partialWidth, 0, partialWidth, 0)

            setPageTransformer(false, MyCardPagerTransformer())

            adapter = MyCardPagerAdapter(this@MyCardActivity)
        }
    }
}
