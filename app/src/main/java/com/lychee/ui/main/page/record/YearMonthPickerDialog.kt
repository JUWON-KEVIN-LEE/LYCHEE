package com.lychee.ui.main.page.record

import android.app.*
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.constraint.R.id.parent
import android.support.v4.app.FragmentManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.NumberPicker
import com.lychee.R
import com.lychee.databinding.YearMonthPickerBinding
import kotlinx.android.synthetic.main.year_month_picker.*
import kotlinx.android.synthetic.main.year_month_picker.view.*
import java.util.*

class YearMonthPickerDialog: android.support.v4.app.DialogFragment() {


    private val MAX_YEAR = 2099
    private val MIN_YEAR = 1980

    private val cal = Calendar.getInstance()

    var mListener: DatePickerDialog.OnDateSetListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?) : Dialog {
        val itemBinding = DataBindingUtil.inflate<YearMonthPickerBinding>(
                LayoutInflater.from(activity),
                R.layout.year_month_picker,
                null,
                false
        )

        with(itemBinding) {
            monthPicker.minValue = 1
            monthPicker.maxValue = 12
            monthPicker.value = arguments?.getInt(ARG_MONTH) ?: cal.get(Calendar.MONTH) + 1

            yearPicker.minValue = MIN_YEAR
            yearPicker.maxValue = MAX_YEAR
            yearPicker.value = arguments?.getInt(ARG_YEAR) ?: cal.get(Calendar.YEAR)

            cancelBtn.setOnClickListener { this@YearMonthPickerDialog.getDialog().dismiss() }
            confirmBtn.setOnClickListener {
                Log.d("LOGLOG", "listener : ${mListener == null}")
                mListener?.onDateSet(null, yearPicker.value, monthPicker.value, 0)

                this@YearMonthPickerDialog.getDialog().dismiss()
            }
        }

         return AlertDialog.Builder(activity)
                 .setView(itemBinding.root)
                 .create()
    }

    companion object {
        val ARG_MONTH = "month"
        val ARG_YEAR = "year"

        fun newInstance(month : Int, year : Int): YearMonthPickerDialog {
            return YearMonthPickerDialog().apply {
                arguments = Bundle().apply {
                    putInt(ARG_MONTH, month)
                    putInt(ARG_YEAR, year)
                }
            }
        }
    }
}




