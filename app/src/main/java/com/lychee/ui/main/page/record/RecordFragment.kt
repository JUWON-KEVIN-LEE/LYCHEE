package com.lychee.ui.main.page.record

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.lychee.R
import com.lychee.databinding.FragmentRecordBinding
import com.lychee.mock.MockData
import com.lychee.ui.base.BaseFragment
import com.lychee.ui.main.page.record.adapter.RecordRecyclerViewAdapter

class RecordFragment : BaseFragment<FragmentRecordBinding, RecordViewModel>() {

    override val layoutResId: Int
        get() = R.layout.fragment_record

    override val viewModelClass: Class<RecordViewModel>
        get() = RecordViewModel::class.java

    override fun onCreateView(savedInstanceState: Bundle?) {
        with(mBinding) {
            recordRecyclerView.apply {
                adapter = RecordRecyclerViewAdapter(MockData.get())
                layoutManager = LinearLayoutManager(context)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        fun newInstance(): RecordFragment = RecordFragment()
    }
}