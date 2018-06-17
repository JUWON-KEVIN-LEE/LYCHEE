package com.lychee.view.map

import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import android.util.DisplayMetrics
import com.google.android.gms.maps.MapView

/**
 * TODO study
 * View 메서드 호출 순서
 * Constructor() | [parent call addView()] | onAttachedToWindow() | [when call requestLayout()] | measure() | onMeasure() | layout() | onLayout() | [when call invalidate()] | dispatchDraw() | draw() | onDraw()
 */
class RoundedMapView : MapView {

    constructor(context : Context) : this(context, null)

    constructor(context : Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)

    constructor(context : Context, attributeSet: AttributeSet?, defStyleAttr : Int) : super(context, attributeSet, defStyleAttr)

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
    }

    override fun dispatchDraw(canvas: Canvas) {
        canvas.let {
            val path = Path()
            val count = it.save()

            val radius =  dp2Px(16f)

            path.addRoundRect(RectF(0f, 0f, width.toFloat(), height.toFloat()), radius, radius, Path.Direction.CW)

            it.clipPath(path)
            super.dispatchDraw(it)
            it.restoreToCount(count)
        }
    }

    private fun dp2Px(dp : Float) : Float
        = dp * ((context.resources.displayMetrics.densityDpi).toFloat() / DisplayMetrics.DENSITY_DEFAULT)
}