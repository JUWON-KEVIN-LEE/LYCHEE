package com.lychee.ui.record

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import com.lychee.R
import com.lychee.data.core.model.Expense
import com.lychee.util.extensions.dpToPx
import com.lychee.util.extensions.spToPx
import java.util.*

class RecordRecyclerViewDecoration(
        private val context: Context,
        private val expenses: List<Expense>
): RecyclerView.ItemDecoration() {

    private val mTextSize = context.spToPx(12)

    private val mBackgroundColor = ContextCompat.getColor(context, R.color.colorSuperLightGrey)

    private val mBackgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = mBackgroundColor
        style = Paint.Style.FILL
    }

    private val mTextPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = mTextSize
        style = Paint.Style.FILL
    }

    private val mDecorationHeight = context.dpToPx(32)

    /**
     * 아이템이 그려지고 나서 오버랩하여 그리는 함수
     */
    override fun onDrawOver(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State?) {
        val left = parent.left + parent.paddingLeft
        val right = parent.right - parent.paddingRight
        var top = 0
        var bottom = mDecorationHeight

        val topView = parent.getChildAt(0)

        val currentPosition = parent.getChildAdapterPosition(topView)

        if(hasDecoration(currentPosition + 1)) {
            val child = parent.getChildAt(1)
            val cTop = child.top
            if(cTop < 2 * mDecorationHeight) {
                top = - (2 * mDecorationHeight) + cTop
                bottom = cTop - mDecorationHeight
            }
        }

        canvas.drawRect(
                0f,
                top.toFloat(),
                right.toFloat(),
                bottom.toFloat(),
                mBackgroundPaint)

        val date = expenses[currentPosition].dateTime

        date?.let {
            val calendar = Calendar.getInstance().apply { time = it }

            val dayOfWeek =
                    when(calendar.get(Calendar.DAY_OF_WEEK)) {
                        Calendar.SUNDAY -> "일"
                        Calendar.MONDAY -> "월"
                        Calendar.TUESDAY -> "화"
                        Calendar.WEDNESDAY -> "수"
                        Calendar.THURSDAY -> "목"
                        Calendar.FRIDAY -> "금"
                        Calendar.SATURDAY -> "토"
                        else -> null
                    }

            val date = "${calendar.get(Calendar.MONTH) + 1}월 ${calendar.get(Calendar.DAY_OF_MONTH)}일 / ${dayOfWeek}요일"

            val fontMetrics = mTextPaint.fontMetrics
            val baseline = (bottom + top) / 2 + (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom

            canvas.drawText(date, context.dpToPx(16f), baseline, mTextPaint)
        }
    }

    /**
     * 아이템이 그려지기 이전에 그리는 함수
     */
    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State?) {
        val left = parent.left /*+ parent.paddingLeft*/
        val right = parent.right - parent.paddingRight

        for(index in 0 until parent.childCount) {
            val child = parent.getChildAt(index)

            val bottom = child.top
            val top = bottom - mDecorationHeight

            val currentPosition = parent.getChildAdapterPosition(child)

            if(hasDecoration(currentPosition)) {
                canvas.drawRect(
                        0f,
                        top.toFloat(),
                        right.toFloat(),
                        bottom.toFloat(),
                        mBackgroundPaint)

                val date = expenses[currentPosition].dateTime

                date?.let {
                    val calendar = Calendar.getInstance().apply { time = it }

                    val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
                    val real = when(dayOfWeek) {
                        Calendar.SUNDAY -> "일"
                        Calendar.MONDAY -> "월"
                        Calendar.TUESDAY -> "화"
                        Calendar.WEDNESDAY -> "수"
                        Calendar.THURSDAY -> "목"
                        Calendar.FRIDAY -> "금"
                        Calendar.SATURDAY -> "토"
                        else -> null
                    }

                    val date = "${calendar.get(Calendar.MONTH) + 1}월 ${calendar.get(Calendar.DAY_OF_MONTH)}일 / ${real}요일"

                    val fontMetrics = mTextPaint.fontMetrics
                    val baseline = (bottom + top) / 2 + (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom

                    canvas.drawText(date, context.dpToPx(16f), baseline, mTextPaint)
                }
            }
        }
    }

    private fun hasDecoration(position: Int): Boolean {
        return position == 0 || expenses[position].dateTime != expenses[position - 1].dateTime
    }

    /**
     * Item 의 영역을 설정
     */
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State?) {
        val currentPosition = parent.getChildAdapterPosition(view)
        if(hasDecoration(currentPosition)) {
            outRect.top = mDecorationHeight
        }
    }
}