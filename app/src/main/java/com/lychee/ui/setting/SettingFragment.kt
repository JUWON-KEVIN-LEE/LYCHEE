package com.lychee.ui.setting

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import com.lychee.R
import com.lychee.databinding.FragmentSettingBinding
import com.lychee.ui.base.ui.BaseFragment
import com.lychee.ui.main.NavigationFragment
import com.lychee.ui.settings.MyCardActivity

class SettingFragment:
        BaseFragment<FragmentSettingBinding, SettingViewModel>(),
        NavigationFragment
{

    override val layoutResId: Int
        get() = R.layout.fragment_setting

    override val viewModelClass: Class<SettingViewModel>
        get() = SettingViewModel::class.java

    override fun FragmentSettingBinding.onCreateView(
            container: ViewGroup?,
            savedInstanceState: Bundle?)
    {
        settingMyCardManagement.setOnClickListener { openMyCardActivity() }
    }

    private fun openMyCardActivity() {
        requireActivity().run {
            startActivity(Intent(this, MyCardActivity::class.java))
        }
    }

    companion object {
        val TAG = SettingFragment::class.java.simpleName

        fun newInstance(): SettingFragment = SettingFragment()
    }
}