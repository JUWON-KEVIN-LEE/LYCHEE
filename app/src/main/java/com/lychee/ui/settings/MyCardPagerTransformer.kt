package com.lychee.ui.settings

import android.support.v4.view.ViewCompat
import android.support.v4.view.ViewPager
import android.view.View

/**
 * Reference
 * : https://jayrambhia.com/blog/android-viewpager-cards-1
 */
class MyCardPagerTransformer: ViewPager.PageTransformer {

    private val baseElevation = 4
    private val raisingElevation = 8

    private val smallerScale = .8f

    override fun transformPage(page: View, position: Float) {
        val absPosition = Math.abs(position)

        if(absPosition >= 1) {
            ViewCompat.setElevation(page, baseElevation.toFloat())
            page.scaleY = smallerScale
        } else {
            ViewCompat.setElevation(page,  (1 - absPosition) * raisingElevation + baseElevation)
            page.scaleY = (smallerScale - 1) * absPosition + 1
        }
    }
}