package com.lychee.ui.main.page.map.adapter

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
            setGuidelineBegin(R.id.mapTopGuideline, context.dpToPx(40))
            setGuidelinePercent(R.id.mapStartGuideline, 0f)
            setGuidelinePercent(R.id.mapEndGuideline, 1f)
        }

        TransitionManager.beginDelayedTransition(this, ChangeBounds().apply {
            duration = 200L
            interpolator = AccelerateDecelerateInterpolator()
        })
    } else {
        update {
            setGuidelinePercent(R.id.mapTopGuideline, -.5f)
            setGuidelinePercent(R.id.mapStartGuideline, -.5f)
            setGuidelinePercent(R.id.mapEndGuideline, 1.5f)
        }

        TransitionManager.beginDelayedTransition(this, ChangeBounds().apply {
            duration = 200L
            interpolator = AccelerateDecelerateInterpolator()
        })
    }
}

@BindingAdapter("expandDetailPage")
fun ConstraintLayout.expandDetailPage(expand: Boolean) {
    if(expand) {
        update {
            setGuidelinePercent(R.id.mapTopGuideline, -.5f)
            setGuidelinePercent(R.id.mapBottomGuideline, .4f)
            setGuidelinePercent(R.id.mapStartGuideline, -.5f)
            setGuidelinePercent(R.id.mapEndGuideline, 1.5f)
        }

        TransitionManager.beginDelayedTransition(this, ChangeBounds().apply {
            duration = 500L
            interpolator = AccelerateDecelerateInterpolator()
        })
    } else {
        update {
            setGuidelineBegin(R.id.mapTopGuideline, context.dpToPx(32))
            setGuidelinePercent(R.id.mapBottomGuideline, 1f)
            setGuidelinePercent(R.id.mapStartGuideline, 0f)
            setGuidelinePercent(R.id.mapEndGuideline, 1f)
        }

        TransitionManager.beginDelayedTransition(this, ChangeBounds().apply {
            duration = 500L
            interpolator = AccelerateDecelerateInterpolator()
        })
    }
}