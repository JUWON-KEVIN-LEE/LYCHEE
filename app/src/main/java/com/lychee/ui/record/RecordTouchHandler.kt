package com.lychee.ui.record

import android.view.MotionEvent
import android.view.View

class RecordTouchHandler(
        private val listener: TouchHandlerListener
// LISTENER
): View.OnTouchListener {

    private var mY: Float = 0f

    override fun onTouch(view: View, event: MotionEvent): Boolean {

        val action = event.action

        when(action) {
            MotionEvent.ACTION_DOWN -> {
                mY = event.rawY
            }
            MotionEvent.ACTION_MOVE -> {
                val deltaY = event.rawY - mY // positive -> from top to bottom

                listener.onMove(deltaY)

                mY = event.rawY
            }
            MotionEvent.ACTION_UP -> {
                mY = 0f

                listener.onEnd()
            }
        }

        return true
    }

    interface TouchHandlerListener {

        fun onMove(deltaY: Float)

        fun onEnd()
    }
}