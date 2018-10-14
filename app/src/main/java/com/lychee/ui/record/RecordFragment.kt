package com.lychee.ui.record

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.transition.ChangeBounds
import android.support.transition.Transition
import android.support.transition.TransitionManager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import com.lychee.R
import com.lychee.data.core.model.Expense
import com.lychee.databinding.FragmentRecordBinding
import com.lychee.ui.base.ui.BaseFragment
import com.lychee.ui.main.NavigationFragment
import com.lychee.util.extensions.dpToPx
import com.lychee.util.extensions.update
import com.lychee.util.extensions.visible
import java.util.*
import kotlin.math.roundToInt

class RecordFragment:
        BaseFragment<FragmentRecordBinding, RecordViewModel>(),
        NavigationFragment
{

    override val layoutResId: Int
        get() = R.layout.fragment_record

    override val viewModelClass: Class<RecordViewModel>
        get() = RecordViewModel::class.java

    private val mCalendar = Calendar.getInstance()

    override fun FragmentRecordBinding.onCreateView(
            container: ViewGroup?,
            savedInstanceState: Bundle?)
    {
        recordRecyclerView.apply {
            adapter = RecordRecyclerViewAdapter(context)
            layoutManager = LinearLayoutManager(context)
        }

        recordDateTextView.setOnClickListener {
            val dialog = YearMonthPickerDialog.newInstance(
                    mViewModel.month.value ?: mCalendar.get(Calendar.MONTH)+1,
                    mViewModel.year.value ?: mCalendar.get(Calendar.YEAR)
            )

            with(dialog) {
                onDateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, _ ->
                    mViewModel.year.value = year
                    mViewModel.month.value = month
                }
                val supportFragmentManger = (requireActivity() as AppCompatActivity).supportFragmentManager
                show(supportFragmentManger, YearMonthPickerDialog.TAG)
            }
        }

        recordDateLeftImageView.setOnClickListener {
            if(mViewModel.month.value == 1) {
                mViewModel.month.value = 12
                mViewModel.year.value = mViewModel.year.value?.dec()
            }
            else {
                mViewModel.month.value = mViewModel.month.value?.dec()
            }
        }

        recordDateRightImageView.setOnClickListener {
            if(mViewModel.month.value == 12) {
                mViewModel.month.value = 1
                mViewModel.year.value = mViewModel.year.value?.inc()
            }
            else {
                mViewModel.month.value = mViewModel.month.value?.inc()
            }
        }

        val touchHandlerListener = object: RecordTouchHandler.TouchHandlerListener {
            override fun onMove(deltaY: Float) {
                val mContext = requireContext()
                val maxHeight = mContext.dpToPx(204)
                val minHeight = mContext.dpToPx(48)

                val maxWidth = mContext.dpToPx(16)
                val minWidth = 0

                val movedPercent = deltaY / maxHeight

                val horizontalParams
                        = (recordStartGuideline.layoutParams as ConstraintLayout.LayoutParams)
                val verticalParams
                        = (recordTopGuideline.layoutParams as ConstraintLayout.LayoutParams)

                val horizontalMovedTo = (maxWidth * movedPercent).roundToInt() + horizontalParams.guideBegin

                val verticalMoveTo = ((maxHeight - minHeight) * movedPercent).roundToInt() + verticalParams.guideBegin

                recordParentLayout.update {
                    verticalMoveTo
                            .takeIf { it in minHeight..maxHeight }
                            ?.let { margin ->
                                setGuidelineBegin(R.id.recordTopGuideline, margin)
                            }
                            ?:let {
                                if(verticalMoveTo > maxHeight) {
                                    setGuidelineBegin(R.id.recordTopGuideline, maxHeight)
                                } else {
                                    setGuidelineBegin(R.id.recordTopGuideline, minHeight)
                                }
                            }

                    horizontalMovedTo
                            .takeIf { it in minWidth..maxWidth }
                            ?.let { margin ->
                                setGuidelineBegin(R.id.recordStartGuideline, margin)
                                setGuidelineEnd(R.id.recordEndGuideline, margin)
                            }
                            ?:let {
                                if(horizontalMovedTo > maxWidth) {
                                    setGuidelineBegin(R.id.recordStartGuideline, maxWidth)
                                    setGuidelineEnd(R.id.recordEndGuideline, maxWidth)
                                } else {
                                    setGuidelineBegin(R.id.recordStartGuideline, minWidth)
                                    setGuidelineEnd(R.id.recordEndGuideline, minWidth)
                                }
                            }
                }
            }

            override fun onEnd() {
                // full expand or return to origin size
                // if full expanded then mScrollable = true
                val mContext = requireContext()

                val params
                        = (recordStartGuideline.layoutParams as ConstraintLayout.LayoutParams)

                val begin = params.guideBegin

                // Origin State
                if(begin > mContext.dpToPx(16 / 2)) {
                    recordParentLayout.update {
                        setGuidelineBegin(R.id.recordTopGuideline, mContext.dpToPx(204))
                        setGuidelineBegin(R.id.recordStartGuideline, mContext.dpToPx(16))
                        setGuidelineEnd(R.id.recordEndGuideline, mContext.dpToPx(16))
                    }

                    TransitionManager.beginDelayedTransition(recordParentLayout, ChangeBounds().apply {
                        duration = 250L
                        interpolator = LinearInterpolator()

                        addListener(object: Transition.TransitionListener {
                            override fun onTransitionEnd(transition: Transition) {
                                recordRecyclerView.mScrollable = false
                                recordRecyclerView.onMoveFromRecyclerView = false

                                recordYearMonthTextView
                                        .animate()
                                        .alpha(0f)
                                        .setDuration(250L)
                                        .start()
                            }
                            override fun onTransitionResume(transition: Transition) {}
                            override fun onTransitionPause(transition: Transition) {}
                            override fun onTransitionCancel(transition: Transition) {}
                            override fun onTransitionStart(transition: Transition) {}
                        })
                    })
                // Expand State
                } else {
                    recordParentLayout.update {
                        setGuidelineBegin(R.id.recordTopGuideline, mContext.dpToPx(48))
                        setGuidelineBegin(R.id.recordStartGuideline, 0)
                        setGuidelineEnd(R.id.recordEndGuideline, 0)
                    }

                    TransitionManager.beginDelayedTransition(recordParentLayout, ChangeBounds().apply {
                        duration = 250L
                        interpolator = LinearInterpolator()

                        addListener(object: Transition.TransitionListener {
                            override fun onTransitionEnd(transition: Transition) {
                                recordRecyclerView.mScrollable = true
                                recordRecyclerView.onMoveFromRecyclerView = false

                                recordYearMonthTextView.visible()

                                recordYearMonthTextView
                                        .animate()
                                        .alpha(1f)
                                        .setDuration(250L)
                                        .start()
                            }
                            override fun onTransitionResume(transition: Transition) {}
                            override fun onTransitionPause(transition: Transition) {}
                            override fun onTransitionCancel(transition: Transition) {}
                            override fun onTransitionStart(transition: Transition) {}
                        })
                    })
                }
            }
        }

        val touchHandler = RecordTouchHandler(touchHandlerListener)

        @SuppressLint("ClickableViewAccessibility")
        val forSuppressLint = recordContentCardView.setOnTouchListener(touchHandler)
        recordRecyclerView.mRecordTouchHandlerListener = touchHandlerListener
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mViewModel.date.observe(this, Observer {
            it?.let { date ->
                mBinding.recordDateTextView.text = date
            }
        })

        mViewModel.expenses.observe(this, Observer {
            it?.let { expenses ->
                with(mBinding.recordRecyclerView) {
                    (adapter as? RecordRecyclerViewAdapter)?.apply { this.expenses = expenses as MutableList<Expense> }

                    addItemDecoration(RecordRecyclerViewDecoration(requireContext(), expenses))
                }
            }
        })
    }


    companion object {
        val TAG = RecordFragment::class.java.simpleName

        fun newInstance(): RecordFragment = RecordFragment()
    }
}