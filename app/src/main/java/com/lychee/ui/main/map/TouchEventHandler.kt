package com.lychee.ui.main.map

import android.annotation.SuppressLint
import android.content.Context
import android.view.MotionEvent
import android.view.View

class TouchEventHandler constructor(
        context : Context, private val listener: TouchEventListener
) : View.OnTouchListener {

    // DEVICE
    private val displayMetrics = context.resources.displayMetrics
    private val deviceWidth = displayMetrics.widthPixels
    private val deviceHeight = displayMetrics.heightPixels

    // TOUCH EVENT
    private var direction : TouchEventHandler.Direction = Direction.None()

    private var mLastX = 0f
    private var mLastY = 0f

    private var dx = 0f
    private var dy = 0f

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(view: View, event: MotionEvent): Boolean {
        val action : Int = event.action

        when(action) {
        // x or y (VIEW 내부 좌표)
        // rawX or rawY (스크린 상 좌표)
            MotionEvent.ACTION_DOWN -> {
                mLastX = event.rawX
                mLastY = event.rawY

                dx = view.x - mLastX
                dy = view.y - mLastY
            }
            MotionEvent.ACTION_MOVE -> {
                val x = event.rawX
                val y = event.rawY

                // Moved Per
                val verticalPer = (y - mLastY) / deviceHeight.toFloat()
                val horizontalPer = (x + dx) / deviceWidth.toFloat()

                // Direction
                direction = getDirection(mLastX, mLastY, x, y)
                when(direction) {
                    is Direction.Left, is Direction.Right -> { /* page */ }
                    is Direction.Up -> { listener.moveUp(verticalPer) }
                    is Direction.Down -> { listener.moveDown(verticalPer) }
                }

                mLastX = x
                mLastY = y
            }
            MotionEvent.ACTION_UP -> {
                when(direction) {
                    is Direction.Up -> { listener.expand() }
                    is Direction.Down -> { listener.shrink() }
                }
            }
            MotionEvent.ACTION_CANCEL -> {

            }
        }
        return true
    }

    private fun getDirection(lastX : Float, lastY : Float, movedX : Float, movedY : Float) : Direction {
        val dx = movedX - lastX
        val dy = movedY - lastY

        return if(Math.abs(dx) > Math.abs(dy)) { if(dx > 0) Direction.Right() else Direction.Left() }
               else { if(dy > 0) Direction.Down() else Direction.Up() }
    }

    private sealed class Direction {
        class None : Direction()
        class Right : Direction()
        class Left : Direction()
        class Up : Direction()
        class Down : Direction()
    }

    companion object {
        // 최대 확장값
        const val MAX_LIMIT = 0.25f
        const val MIN_LIMIT = 0.75f

        // 디바이스 기준 .45 만큼 이동시 확장
        const val BOOL = 0.45f
    }
}