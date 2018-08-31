package com.lychee.ui.main.page.record.dialog

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import com.lychee.R
import com.lychee.databinding.YearMonthPickerBinding
import com.lychee.util.extensions.bundleOf
import java.util.*

class YearMonthPickerDialog: android.support.v4.app.DialogFragment() {

    private val calendar = Calendar.getInstance()

    lateinit var onDateSetListener: DatePickerDialog.OnDateSetListener

    override fun onCreateDialog(savedInstanceState: Bundle?) : Dialog {
        val mBinding = DataBindingUtil.inflate<YearMonthPickerBinding>(
                LayoutInflater.from(activity),
                R.layout.year_month_picker,
                null,
                false
        )

        with(mBinding) {
            monthPicker.minValue = 1
            monthPicker.maxValue = 12
            monthPicker.value = arguments?.getInt(ARG_MONTH) ?: calendar.get(Calendar.MONTH) + 1

            yearPicker.minValue = MIN_YEAR
            yearPicker.maxValue = MAX_YEAR
            yearPicker.value = arguments?.getInt(ARG_YEAR) ?: calendar.get(Calendar.YEAR)

            cancelBtn.setOnClickListener { this@YearMonthPickerDialog.dialog.dismiss() }
            confirmBtn.setOnClickListener {
                onDateSetListener.onDateSet(
                        null,
                        yearPicker.value,
                        monthPicker.value,
                        0)

                this@YearMonthPickerDialog.dialog.dismiss()
            }
        }

         return AlertDialog
                 .Builder(activity)
                 .setView(mBinding.root)
                 .create()
    }

    companion object {
        val TAG = YearMonthPickerDialog::class.java.simpleName

        private const val MAX_YEAR = 2022
        private const val MIN_YEAR = 2012

        private const val ARG_MONTH = "month"
        private const val ARG_YEAR = "year"

        fun newInstance(month : Int, year : Int): YearMonthPickerDialog {
            return YearMonthPickerDialog().apply {
                arguments = bundleOf(
                        Pair(ARG_MONTH, month),
                        Pair(ARG_YEAR, year))
            }
        }
    }
}




