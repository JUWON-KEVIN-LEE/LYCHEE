package com.lychee.util.extensions

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.support.annotation.LayoutRes
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lychee.data.common.util.RealmResultsLiveData
import io.realm.RealmObject
import io.realm.RealmResults

/* SCREEN */
fun Context.dpToPx(dp : Float): Float = dp * resources.displayMetrics.density
fun Context.dpToPx(dp : Int): Int = Math.round(dp * resources.displayMetrics.density)
fun Context.spToPx(sp: Int): Float = sp * resources.displayMetrics.scaledDensity

/* VIEW */
fun View.visible() { visibility = View.VISIBLE }
fun View.invisible() { visibility = View.INVISIBLE }
fun View.gone() { visibility = View.GONE }

/* VIEW GROUP */
fun ViewGroup.inflate(@LayoutRes layout: Int, attachToRoot: Boolean = false): View
        = LayoutInflater.from(context).inflate(layout, this, attachToRoot)

/* CONSTRAINT LAYOUT */
fun ConstraintLayout.update(constraintSet: ConstraintSet = ConstraintSet(), update : ConstraintSet.() -> Unit) {
    constraintSet.clone(this)
    constraintSet.update()
    constraintSet.applyTo(this)
}

/* EVENT OR LISTENER CONSUMER */
inline fun consume(work: () -> Unit): Boolean {
    work()
    return true
}

/* COLLECTION */
fun <T> List<T>?.isNotEmptyThen(then: List<T>.() -> Unit) {
    this
            ?.takeIf { it.isNotEmpty() }
            ?.then()
}

/* SMS */
fun <T> tryOrNull(work: () -> T): T? {
    return try { work() }
    catch (exception: Exception) {
        exception.printStackTrace()
        null
    }
}

/* VIEW MODEL */
fun <VM: ViewModel> FragmentActivity.getViewModel(
        provider: ViewModelProvider.Factory,
        classType: Class<VM>
): VM {
    return ViewModelProviders.of(this, provider).get(classType)
}

fun <VM: ViewModel> Fragment.getViewModel(
        provider: ViewModelProvider.Factory,
        classType: Class<VM>
): VM {
    return ViewModelProviders.of(this, provider).get(classType)
}

fun <VM: ViewModel> Fragment.getActivityViewModel(
        provider: ViewModelProvider.Factory,
        classType: Class<VM>
): VM {
    return ViewModelProviders.of(requireActivity(), provider).get(classType)
}

/* LIVE DATA */
fun <T: RealmObject> RealmResults<T>.asLiveData() = RealmResultsLiveData(this)