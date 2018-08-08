package com.lychee.ui.main.page.setting

import android.os.Bundle
import com.lychee.R
import com.lychee.databinding.FragmentSettingBinding
import com.lychee.ui.base.BaseFragment

class SettingFragment : BaseFragment<FragmentSettingBinding, SettingViewModel>() {

    override val layoutResId: Int
        get() = R.layout.fragment_setting

    override val viewModelClass: Class<SettingViewModel>
        get() = SettingViewModel::class.java

    override fun onCreateView(savedInstanceState: Bundle?) {

    }

    companion object {
        fun newInstance(): SettingFragment = SettingFragment()
    }
}