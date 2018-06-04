package com.lychee.ui.main.setting

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lychee.R

class SettingFragment : Fragment() /*: BaseFragment<SettingViewModel>()*/ {

    /*
    override val viewModelClass: Class<SettingViewModel>
        get() = SettingViewModel::class.java
    */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
        = inflater.inflate(R.layout.fragment_setting, container, false)

}