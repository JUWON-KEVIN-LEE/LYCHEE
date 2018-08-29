package com.lychee.ui.main.page.record

import android.app.DatePickerDialog
import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.lychee.R
import com.lychee.data.model.core.Expenditure
import com.lychee.databinding.FragmentRecordBinding
import com.lychee.ui.base.BaseFragment

class RecordFragment : BaseFragment<FragmentRecordBinding, RecordViewModel>() {

    override val layoutResId: Int
        get() = R.layout.fragment_record

    override val viewModelClass: Class<RecordViewModel>
        get() = RecordViewModel::class.java

    override fun onCreateView(savedInstanceState: Bundle?) {

        val listener = object:RecordExpenditureClickListener{
            override fun onClick(expenditure: Expenditure) {
                Toast.makeText(context,"상점명 :  ${expenditure.shopName}", Toast.LENGTH_LONG).show()
            }
        }

        with(mBinding) {
            recordRecyclerView.apply {
                // adapter = RecyclerAdapter( MockData.get(),listener)
                layoutManager = LinearLayoutManager(context)
            }

            recordDateTextView.setOnClickListener {
                val dialog = YearMonthPickerDialog.newInstance(mViewModel.month.value!!, mViewModel.year.value!!)

                dialog.mListener = DatePickerDialog.OnDateSetListener { _, year, month, _ ->
                    mViewModel.month.value = month
                    mViewModel.year.value = year
                }

                dialog.show((mContext as AppCompatActivity).supportFragmentManager, "picker")
            }

            recordDateLeftImageView.setOnClickListener {
                if(mViewModel.month.value == 1) {
                    mViewModel.month.value = 12
                    mViewModel.year.value = mViewModel.year.value?.dec()
                }
                else mViewModel.month.value = mViewModel.month.value?.dec()

            }

            recordDateRightImageView.setOnClickListener {
                if(mViewModel.month.value == 12) {
                    mViewModel.month.value = 1
                    mViewModel.year.value = mViewModel.year.value?.inc()

                }
                else
                    mViewModel.month.value = mViewModel.month.value?.inc()

            }
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel.month.observe(this, Observer {
            mBinding.recordDateTextView.text = "${it}월  ${mViewModel.year.value}"
        })

        mViewModel.year.observe(this, Observer {
            mBinding.recordDateTextView.text = "${mViewModel.month.value}월  $it"
        })
    }


    companion object {
        fun newInstance(): RecordFragment = RecordFragment()
    }
}