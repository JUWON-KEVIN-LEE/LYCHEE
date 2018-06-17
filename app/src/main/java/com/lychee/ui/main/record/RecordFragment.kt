package com.lychee.ui.main.record

import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import com.lychee.R
import com.lychee.data.model.Expenditure
import com.lychee.databinding.FragmentRecordBinding
import com.lychee.mock.MockData
import com.lychee.ui.base.BaseFragment
import com.lychee.ui.main.ActionBarProvider

class RecordFragment : BaseFragment<FragmentRecordBinding, RecordViewModel>(R.layout.fragment_record) {

    override val viewModelClass: Class<RecordViewModel>
        get() = RecordViewModel::class.java

    override fun onCreateView() {
        binding.apply {
            // TODO
            // MONTH PICKER

            // ADD

            // SEARCH

            // EXPENDITURE

            // INCOME

            // RECYCLER VIEW
            recRecord.apply {
                val data = mutableListOf<Expenditure>()
                        .apply {
                            addAll(MockData.get())
                            addAll(MockData.get())
                            addAll(MockData.get())
                            addAll(MockData.get())
                            addAll(MockData.get())
                        }
                adapter = RecordRecAdapter(data)
                layoutManager = LinearLayoutManager(this@RecordFragment.context)
            }
        }

        (mContext as ActionBarProvider).setActionBarColor(ContextCompat.getColor(mContext, R.color.colorPrimary))
    }

    override fun onStart() {
        super.onStart()
    }
}