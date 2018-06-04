package com.lychee.ui.main.record

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lychee.R
import com.lychee.databinding.FragmentRecordBinding
import com.lychee.mock.MockData
import com.lychee.ui.base.BaseFragment

class RecordFragment : BaseFragment<FragmentRecordBinding, RecordViewModel>(R.layout.fragment_record) {

    override val viewModelClass: Class<RecordViewModel>
        get() = RecordViewModel::class.java

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View
        = super.onCreateView(inflater, container, savedInstanceState)

    override fun init() {
        binding.apply {
            // TODO
            // MONTH PICKER

            // ADD

            // SEARCH

            // EXPENDITURE

            // INCOME

            // RECYCLER VIEW
            recRecord.apply {
                adapter = RecordRecAdapter(MockData.get())
                layoutManager = LinearLayoutManager(this@RecordFragment.context)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // TODO
    }
}