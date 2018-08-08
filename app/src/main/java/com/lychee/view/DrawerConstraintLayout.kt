package com.lychee.view

import android.animation.ObjectAnimator
import android.content.Context
import android.support.annotation.FloatRange
import android.support.constraint.ConstraintLayout
import android.support.transition.ChangeBounds
import android.support.transition.Transition
import android.support.transition.TransitionManager
import android.support.v7.graphics.drawable.DrawerArrowDrawable
import android.util.AttributeSet
import android.util.Property
import android.view.MotionEvent
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import android.widget.ImageView
import com.lychee.R
import com.lychee.util.extensions.dpToPx
import com.lychee.util.extensions.update
import kotlin.properties.Delegates

class DrawerConstraintLayout: ConstraintLayout, DrawerViewPager.OnSwipeOutListener {

    private val drawerArrowDrawable: DrawerArrowDrawable by lazy {
        return@lazy drawerButton.drawable as DrawerArrowDrawable
    }

    private val drawerButton: ImageView by lazy {
        findViewById<ImageView>(R.id.mainDrawerButton)
    }

    private val foregroundAlphaLayout: FrameLayout by lazy {
        findViewById<FrameLayout>(R.id.mainForegroundLayout)
    }

    var drawerArrowDrawableProgress: Float by Delegates.observable(0f) {_, oldValue, newValue ->
        if(newValue > 1f) {
            drawerArrowDrawableProgress = 1f
            return@observable
        } else if(newValue < 0f) {
            drawerArrowDrawableProgress = 0f
            return@observable
        }

        if(oldValue == 0f && newValue == 1f) {
            openDrawer()
            drawerIconAnimate(oldValue, newValue)
            foregroundAlphaAnimate(newValue)
        }
        else if (oldValue == 1f && newValue == 0f) {
            closeDrawer()
            drawerIconAnimate(oldValue, newValue)
            foregroundAlphaAnimate(newValue)
        }
        else if(Math.abs(oldValue - newValue) > .1) {
            if(newValue == 1f) {
                openDrawer()
                drawerIconAnimate(oldValue, newValue)
                foregroundAlphaAnimate(newValue)
            } else if(newValue == 0f) {
                closeDrawer()
                drawerIconAnimate(oldValue, newValue)
                foregroundAlphaAnimate(newValue)
            }
        }
        else {
            onMoveDrawer(newValue)
        }
    }

    private val animationProperty
            = object: Property<DrawerArrowDrawable, Float>(Float::class.java, "progress") {
        override fun set(drawable: DrawerArrowDrawable, value: Float) { drawable.progress = value }
        override fun get(drawable: DrawerArrowDrawable): Float = drawable.progress
    }

    private val transition: Transition = ChangeBounds()
            .apply {
                duration = 500L
                interpolator = LinearInterpolator()
            }

    /* TOUCH EVENT */
    private val menuWidth = context.dpToPx(180)

    private var mX: Float = 0f

    private var clickDrawerButton: Boolean = false

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private fun openDrawer() {
        update {
            setGuidelineBegin(R.id.mainStartGuideline, context.dpToPx(196))
            setGuidelinePercent(R.id.mainTopGuideline, .1f)
            setGuidelinePercent(R.id.mainBottomGuideline, .9f)
            setGuidelinePercent(R.id.mainEndGuideline, 1.5f)
        }

        TransitionManager.beginDelayedTransition (this, transition)
    }

    private fun closeDrawer() {
        update {
            setGuidelineBegin(R.id.mainStartGuideline, 0)
            setGuidelinePercent(R.id.mainTopGuideline, 0f)
            setGuidelinePercent(R.id.mainBottomGuideline, 1f)
            setGuidelinePercent(R.id.mainEndGuideline, 1f)
        }

        TransitionManager.beginDelayedTransition (this, transition)
    }

    private fun drawerIconAnimate(from: Float, to: Float) {
        val animator = ObjectAnimator.ofFloat(drawerArrowDrawable, animationProperty, from, to)
        animator.interpolator = AccelerateDecelerateInterpolator()
        animator.duration = 500L
        animator.start()
    }

    private fun foregroundAlphaAnimate(to: Float)
            = foregroundAlphaLayout.animate().alpha(to).setDuration(500L).setInterpolator(LinearInterpolator()).start()

    private fun onMoveDrawer(@FloatRange(from = 0.0, to = 1.0) progress: Float) {
        val startTo = context.dpToPx(196 * progress).toInt()
        val topTo = .1f * progress
        val bottomTo = 1f + -.1f * progress
        val endTo = 1f + .5f * progress

        update {
            setGuidelineBegin(R.id.mainStartGuideline, startTo)
            setGuidelinePercent(R.id.mainTopGuideline, topTo)
            setGuidelinePercent(R.id.mainBottomGuideline, bottomTo)
            setGuidelinePercent(R.id.mainEndGuideline, endTo)
        }

        drawerArrowDrawable.progress = progress

        foregroundAlphaLayout.alpha = progress
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        return drawerArrowDrawableProgress != 0f // not close state
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {

        when(event.action) {
            MotionEvent.ACTION_DOWN -> {
                mX = event.rawX

                val pointer = IntArray(2)
                drawerButton.getLocationOnScreen(pointer)

                if(event.rawX > pointer[0] && event.rawX < pointer[0] + drawerButton.width
                        && event.rawY > pointer[1] && event.rawY < pointer[1] + drawerButton.height) {
                    clickDrawerButton = true
                }
            }
            MotionEvent.ACTION_MOVE -> {
                val dX = event.rawX - mX
                val progress = dX / menuWidth

                drawerArrowDrawableProgress += progress

                mX = event.rawX
            }
            MotionEvent.ACTION_UP -> {
                val pointer = IntArray(2)
                drawerButton.getLocationOnScreen(pointer)

                if(event.rawX > pointer[0] && event.rawX < pointer[0] + drawerButton.width
                        && event.rawY > pointer[1] && event.rawY < pointer[1] + drawerButton.height
                        && clickDrawerButton) {
                    drawerArrowDrawableProgress = 0f
                } else {
                    if(drawerArrowDrawableProgress >= .5f && drawerArrowDrawableProgress < 1f) {
                        drawerArrowDrawableProgress = 1f
                    } else if(drawerArrowDrawableProgress > 0f && drawerArrowDrawableProgress < .5f) {
                        drawerArrowDrawableProgress = 0f
                    }
                }

                mX = 0f
            }
            MotionEvent.ACTION_CANCEL -> {
                if(drawerArrowDrawableProgress >= .5f && drawerArrowDrawableProgress < 1f) {
                    drawerArrowDrawableProgress = 1f
                } else if(drawerArrowDrawableProgress > 0f && drawerArrowDrawableProgress < .5f) {
                    drawerArrowDrawableProgress = 0f
                }

                mX = 0f
            }
        }

        return true
    }

    override fun onSwipe(progress: Float) {
        drawerArrowDrawableProgress += progress
    }

    override fun endSwipe() {
        if(drawerArrowDrawableProgress >= .5f && drawerArrowDrawableProgress < 1f) {
            drawerArrowDrawableProgress = 1f
        } else if(drawerArrowDrawableProgress > 0f && drawerArrowDrawableProgress < .5f) {
            drawerArrowDrawableProgress = 0f
        }
    }
}