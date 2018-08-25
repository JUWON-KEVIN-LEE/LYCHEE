package com.lychee.view.main

/*
class DrawerConstraintLayout: ConstraintLayout/*, DrawerViewPager.OnSwipeOutListener */{

    private val drawerArrowDrawable: DrawerArrowDrawable by lazy { drawerButton.drawable as DrawerArrowDrawable }

    private val mapDrawerArrowDrawable: DrawerArrowDrawable by lazy { mapDrawerButton.drawable as DrawerArrowDrawable }

    private val mapDrawerButton: ImageView by lazy { findViewById<ImageView>(R.id.mapDrawerButton) }

    private val drawerButton: ImageView by lazy { findViewById<ImageView>(R.id.mainDrawerButton) }

    private val foregroundAlphaLayout: FrameLayout by lazy { findViewById<FrameLayout>(R.id.mainForegroundLayout)}

    var fromMapDrawer = false

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

    private val mDuration = 200L

    private val transition: Transition = ChangeBounds()
            .apply {
                duration = mDuration
                interpolator = LinearInterpolator()
            }

    /* TOUCH EVENT */
    private val deviceWidth = context.resources.displayMetrics.widthPixels
    private val menuWidth = context.dpToPx(240)

    private var mX: Float = 0f

    private var clickDrawerButton: Boolean = false

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private fun openDrawer() {
        update {
            setGuidelineBegin(R.id.mainStartGuideline, menuWidth)
            // setGuidelinePercent(R.id.mainTopGuideline, .1f)
            // setGuidelinePercent(R.id.mainBottomGuideline, .9f)
            setGuidelineBegin(R.id.mainEndGuideline, deviceWidth + menuWidth)
            // setGuidelinePercent(R.id.mainEndGuideline, 1.5f)
        }

        TransitionManager.beginDelayedTransition (this, transition)
    }

    private fun closeDrawer() {
        update {
            setGuidelineBegin(R.id.mainStartGuideline, 0)
            // setGuidelinePercent(R.id.mainTopGuideline, 0f)
            // setGuidelinePercent(R.id.mainBottomGuideline, 1f)
            setGuidelinePercent(R.id.mainEndGuideline, 1f)
        }

        TransitionManager.beginDelayedTransition (this, transition)
    }

    private fun drawerIconAnimate(from: Float, to: Float) {
        if(fromMapDrawer) {
            val mapAnimator = ObjectAnimator.ofFloat(mapDrawerArrowDrawable, animationProperty, from, to)
            mapAnimator.interpolator = AccelerateDecelerateInterpolator()
            mapAnimator.duration = mDuration
            mapAnimator.start()
            return
        }

        val animator = ObjectAnimator.ofFloat(drawerArrowDrawable, animationProperty, from, to)
        animator.interpolator = AccelerateDecelerateInterpolator()
        animator.duration = mDuration
        animator.start()
    }

    private fun foregroundAlphaAnimate(to: Float)
            = foregroundAlphaLayout.animate().alpha(to).setDuration(mDuration).setInterpolator(LinearInterpolator()).start()

    private fun onMoveDrawer(@FloatRange(from = 0.0, to = 1.0) progress: Float) {
        val moved = Math.round(menuWidth * progress)

        update {
            setGuidelineBegin(R.id.mainStartGuideline, moved)
            // setGuidelinePercent(R.id.mainTopGuideline, topTo)
            // setGuidelinePercent(R.id.mainBottomGuideline, bottomTo)
            setGuidelineBegin(R.id.mainEndGuideline, deviceWidth + moved)
        }

        if(fromMapDrawer) mapDrawerArrowDrawable.progress = progress
        else drawerArrowDrawable.progress = progress

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

                if(fromMapDrawer) {
                    mapDrawerButton.getLocationOnScreen(pointer)

                    if(event.rawX > pointer[0] && event.rawX < pointer[0] + mapDrawerButton.width
                            && event.rawY > pointer[1] && event.rawY < pointer[1] + mapDrawerButton.height) {
                        clickDrawerButton = true
                    }
                }
                else {
                    drawerButton.getLocationOnScreen(pointer)

                    if(event.rawX > pointer[0] && event.rawX < pointer[0] + drawerButton.width
                            && event.rawY > pointer[1] && event.rawY < pointer[1] + drawerButton.height) {
                        clickDrawerButton = true
                    }
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

                if(fromMapDrawer) {
                    mapDrawerButton.getLocationOnScreen(pointer)

                    if(event.rawX > pointer[0] && event.rawX < pointer[0] + mapDrawerButton.width
                            && event.rawY > pointer[1] && event.rawY < pointer[1] + mapDrawerButton.height
                            && clickDrawerButton) {
                        drawerArrowDrawableProgress = 0f
                    }  else {
                        if(drawerArrowDrawableProgress >= .5f && drawerArrowDrawableProgress < 1f) {
                            drawerArrowDrawableProgress = 1f
                        } else if(drawerArrowDrawableProgress > 0f && drawerArrowDrawableProgress < .5f) {
                            drawerArrowDrawableProgress = 0f
                        }
                    }
                } else {
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
                }

                clickDrawerButton = false

                mX = 0f
            }
            MotionEvent.ACTION_CANCEL -> {
                if(drawerArrowDrawableProgress >= .5f && drawerArrowDrawableProgress < 1f) {
                    drawerArrowDrawableProgress = 1f
                } else if(drawerArrowDrawableProgress > 0f && drawerArrowDrawableProgress < .5f) {
                    drawerArrowDrawableProgress = 0f
                }

                clickDrawerButton = false

                mX = 0f
            }
        }

        return true
    }
}
*/