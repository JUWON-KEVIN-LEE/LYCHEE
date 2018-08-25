package com.lychee.util.extensions

import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.support.constraint.Group
import android.util.Log
import android.view.View

/**
 * View Extensions
 */
// VISIBILITY
fun View.visible() { visibility = View.VISIBLE }

fun View.invisible() { visibility = View.INVISIBLE }

fun View.gone() { visibility = View.GONE }

// CONSTRAINT LAYOUT
fun ConstraintLayout.update(constraintSet: ConstraintSet = ConstraintSet(), update : ConstraintSet.() -> Unit) {
    constraintSet.clone(this)
    constraintSet.update()
    constraintSet.applyTo(this)
}

fun Group.setAllOnClickListener(listener: (view: View) -> Unit) {
    Log.d("JUWONLEE", "setAllOnClickListener")
    referencedIds.forEach {
        rootView.findViewById<View>(it).setOnClickListener(listener)
    }
}