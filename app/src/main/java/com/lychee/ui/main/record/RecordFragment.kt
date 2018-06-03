package com.lychee.ui.main.record

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lychee.R
import com.lychee.ui.base.BaseFragment

class RecordFragment : BaseFragment<RecordViewModel>() {

    override val viewModelClass: Class<RecordViewModel>
        get() = RecordViewModel::class.java

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
        = inflater.inflate(R.layout.fragment_record, container, false)

}