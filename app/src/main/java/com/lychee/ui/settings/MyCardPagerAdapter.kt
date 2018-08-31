package com.lychee.ui.settings

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v4.content.ContextCompat
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lychee.R
import com.lychee.databinding.ItemViewPagerMyCardBinding

class MyCardPagerAdapter constructor(
        private val context: Context
): PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val mBinding = DataBindingUtil.inflate<ItemViewPagerMyCardBinding>(
                LayoutInflater.from(context),
                R.layout.item_view_pager_my_card,
                container,
                false)

        // TEST
        if(position % 2 != 0) {
            mBinding.itemMyCardParentLayout.setCardBackgroundColor(ContextCompat.getColor(context, R.color.colorAccentGreen))
        }

        container.addView(mBinding.root)

        return mBinding.root
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean = (view == `object`)

    override fun getCount(): Int  = 5

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        (`object` as? View)
                ?.let { view -> container.removeView(view) }
    }
}