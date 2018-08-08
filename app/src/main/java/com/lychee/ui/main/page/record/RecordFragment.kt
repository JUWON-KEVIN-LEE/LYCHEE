package com.lychee.ui.main.page.record

import android.os.Bundle
import com.lychee.R
import com.lychee.databinding.FragmentRecordBinding
import com.lychee.ui.base.BaseFragment

class RecordFragment : BaseFragment<FragmentRecordBinding, RecordViewModel>() {

    override val layoutResId: Int
        get() = R.layout.fragment_record

    override val viewModelClass: Class<RecordViewModel>
        get() = RecordViewModel::class.java

    override fun onCreateView(savedInstanceState: Bundle?) {

    }

    companion object {
        fun newInstance(): RecordFragment = RecordFragment()
    }
}