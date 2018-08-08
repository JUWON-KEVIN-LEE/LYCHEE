package com.lychee.util.extensions

import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * View Extensions
 */
// VISIBILITY
fun View.visible() { visibility = View.VISIBLE }

fun View.invisible() { visibility = View.INVISIBLE }

fun View.gone() { visibility = View.GONE }

// VIEW GROUP
fun ViewGroup.replaceFragment(supportFragmentManager: FragmentManager, fragment: Fragment)
    = supportFragmentManager.beginTransaction().replace(this.id, fragment).commit()

fun ViewGroup.removeFragment(supportFragmentManager: FragmentManager, fragment: Fragment)
    = supportFragmentManager.beginTransaction().remove(fragment).commit()

fun ViewGroup.inflate(resId : Int) : View
    = inflate(resId, false)

fun ViewGroup.inflate(resId : Int, attachToRoot : Boolean) : View
        = LayoutInflater.from(context).inflate(resId, this, attachToRoot)

// CONSTRAINT LAYOUT
fun ConstraintLayout.update(constraintSet: ConstraintSet = ConstraintSet(), update : ConstraintSet.() -> Unit) {
    constraintSet.clone(this)
    constraintSet.update()
    constraintSet.applyTo(this)
}