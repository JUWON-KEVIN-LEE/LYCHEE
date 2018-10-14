package com.lychee.ui.map

import android.databinding.BindingAdapter
import android.support.constraint.ConstraintLayout
import android.support.transition.ChangeBounds
import android.support.transition.TransitionManager
import android.view.animation.AccelerateDecelerateInterpolator
import com.lychee.R
import com.lychee.util.extensions.dpToPx
import com.lychee.util.extensions.update

@BindingAdapter("controllerVisibility")
fun ConstraintLayout.controllerVisibility(visible: Boolean) {
    if(visible) {
        update {
            setGuidelineBegin(R.id.mapTopGuideline, context.dpToPx(88))
            setGuidelineEnd(R.id.mapEndGuideline, 0)
        }

        TransitionManager.beginDelayedTransition(this, ChangeBounds().apply {
            duration = 500L
            interpolator = AccelerateDecelerateInterpolator()
        })
    } else {
        update {
            setGuidelineBegin(R.id.mapTopGuideline, 0)
            setGuidelinePercent(R.id.mapEndGuideline, 1.5f)
        }

        TransitionManager.beginDelayedTransition(this, ChangeBounds().apply {
            duration = 500L
            interpolator = AccelerateDecelerateInterpolator()
        })
    }
}