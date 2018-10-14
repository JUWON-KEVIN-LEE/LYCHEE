package com.lychee.ui.add

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import java.util.*


class AddDatePicker: DialogFragment() {

    lateinit var mOnDateSetListener: DatePickerDialog.OnDateSetListener

    private val calendar = Calendar.getInstance()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        return DatePickerDialog(activity, mOnDateSetListener, year, month, day)
    }

    companion object {
        val TAG = AddDatePicker::class.java.simpleName

        fun newInstance(
                onDateSetListener: DatePickerDialog.OnDateSetListener,
                year: Int,
                month: Int,
                date: Int,
                initial: Boolean): AddDatePicker {

            val picker = AddDatePicker()

            picker.mOnDateSetListener = onDateSetListener

            if(!initial) {
                picker.calendar.set(year, month, date)
            }

            return picker
        }
    }
}