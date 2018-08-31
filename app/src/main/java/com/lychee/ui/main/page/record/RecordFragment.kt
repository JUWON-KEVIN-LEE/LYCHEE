package com.lychee.ui.main.page.record

import android.app.DatePickerDialog
import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.lychee.R
import com.lychee.data.core.model.Expenditure
import com.lychee.databinding.FragmentRecordBinding
import com.lychee.ui.base.BaseFragment
import com.lychee.ui.main.page.record.adapter.RecordRecyclerViewAdapter
import com.lychee.ui.main.page.record.dialog.YearMonthPickerDialog
import java.util.*

class RecordFragment : BaseFragment<FragmentRecordBinding, RecordViewModel>() {

    override val layoutResId: Int
        get() = R.layout.fragment_record

    override val viewModelClass: Class<RecordViewModel>
        get() = RecordViewModel::class.java

    private val mCalendar = Calendar.getInstance()

    override fun onCreateView(savedInstanceState: Bundle?) {
        with(mBinding) {
            recordRecyclerView.apply {
                adapter = RecordRecyclerViewAdapter(context)
                layoutManager = LinearLayoutManager(context)
            }

            recordDateTextView.setOnClickListener {
                val dialog = YearMonthPickerDialog.newInstance(
                        mViewModel.month.value ?: mCalendar.get(Calendar.MONTH) + 1,
                        mViewModel.year.value ?: mCalendar.get(Calendar.YEAR)
                )

                with(dialog) {
                    onDateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, _ ->
                        mViewModel.year.value = year
                        mViewModel.month.value = month
                    }
                    val supportFragmentManger = (mContext as AppCompatActivity).supportFragmentManager
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
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mViewModel.date.observe(this, Observer {
            it?.let { date ->
                mBinding.recordDateTextView.text = date
            }
        })

        mViewModel.expenditures.observe(this, Observer {
            it?.let { expenditures ->
                val adapter = (mBinding.recordRecyclerView.adapter as RecordRecyclerViewAdapter)
                adapter.expenditures = expenditures as MutableList<Expenditure>
            }
        })

        mViewModel.fetchExpenditures()
    }


    companion object {
        fun newInstance(): RecordFragment = RecordFragment()
    }
}