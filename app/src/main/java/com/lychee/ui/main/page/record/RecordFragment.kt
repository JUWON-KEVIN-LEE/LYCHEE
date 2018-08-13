package com.lychee.ui.main.page.record

import android.content.ClipData
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.LinearLayout
import com.lychee.R
import com.lychee.data.model.Expenditure
import com.lychee.databinding.FragmentRecordBinding
import com.lychee.mock.MockData
import com.lychee.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_record.*

class RecordFragment : BaseFragment<FragmentRecordBinding, RecordViewModel>() {



    override val layoutResId: Int
        get() = R.layout.fragment_record

    override val viewModelClass: Class<RecordViewModel>
        get() = RecordViewModel::class.java

    override fun onCreateView(savedInstanceState: Bundle?) {

        with(mBinding) {
            recordRecyclerView.apply {
                adapter = RecyclerAdapter( MockData.get())
                layoutManager = LinearLayoutManager(context)
            }
        }


    }

    companion object {
        fun newInstance(): RecordFragment = RecordFragment()
    }
}