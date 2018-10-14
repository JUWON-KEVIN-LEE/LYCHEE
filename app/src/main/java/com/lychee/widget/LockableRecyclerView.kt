package com.lychee.widget

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.MotionEvent
import com.lychee.ui.record.RecordTouchHandler

class LockableRecyclerView: RecyclerView {

    var mRecordTouchHandlerListener: RecordTouchHandler.TouchHandlerListener? = null

    var mScrollable: Boolean = false

    private var mY: Float = 0f

    var onMoveFromRecyclerView: Boolean = false

    constructor(context: Context?) : super(context)

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when(event.action) {
            MotionEvent.ACTION_DOWN -> {
                if(mScrollable) {
                    mY = event.rawY

                    super.onTouchEvent(event)
                }

                return mScrollable
            }
            MotionEvent.ACTION_MOVE -> {
                return if(mScrollable) {
                    val mLayoutManager = (layoutManager as LinearLayoutManager)

                    val deltaY = event.rawY - mY

                    if((onMoveFromRecyclerView || deltaY > 0) && mLayoutManager.findFirstCompletelyVisibleItemPosition() == 0) {
                        if(deltaY < 0) {
                            onMoveFromRecyclerView = false

                            return super.onTouchEvent(event)
                        }

                        onMoveFromRecyclerView = true

                        mRecordTouchHandlerListener?.onMove(deltaY)

                        mY = event.rawY

                        true
                    } else {
                        super.onTouchEvent(event)
                    }
                } else {
                    /* Unreachable */
                    return mScrollable
                }
            }
            MotionEvent.ACTION_UP -> {
                return if(onMoveFromRecyclerView) {
                    mRecordTouchHandlerListener?.onEnd()

                    onMoveFromRecyclerView = false

                    true
                } else {
                    super.onTouchEvent(event)
                }
            }
            else -> return super.onTouchEvent(event)
        }
    }

    override fun onInterceptTouchEvent(e: MotionEvent?): Boolean {
        if(!mScrollable) return false

        return super.onInterceptTouchEvent(e)
    }
}