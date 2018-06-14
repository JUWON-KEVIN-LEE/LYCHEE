package com.lychee.ui.main.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lychee.R
import com.lychee.databinding.FragmentSettingBinding
import com.lychee.ui.base.BaseFragment
import com.lychee.ui.cardlist.CardListActivity

class SettingFragment : BaseFragment<FragmentSettingBinding, SettingViewModel>(R.layout.fragment_setting) {

    override val viewModelClass: Class<SettingViewModel>
        get() = SettingViewModel::class.java

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View
        = super.onCreateView(inflater, container, savedInstanceState)

    override fun init() {
        binding.apply {
            managingCard.setOnClickListener { CardListActivity.start(mContext) }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
    }

}