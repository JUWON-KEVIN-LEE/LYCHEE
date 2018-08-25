package com.lychee.ui.main.page.home

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.lychee.R
import com.lychee.databinding.FragmentHomeBinding
import com.lychee.mock.MockData
import com.lychee.ui.base.BaseFragment
import com.lychee.ui.main.page.home.adapter.HomeRecentRecyclerViewAdapter

/**
 * Home 화면
 */
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    override val layoutResId: Int
        get() = R.layout.fragment_home

    override val viewModelClass: Class<HomeViewModel>
        get() = HomeViewModel::class.java

    override fun onCreateView(savedInstanceState: Bundle?) {
        with(mBinding) {
            // home nav image view click > animation + activity listen
            homeRecentRecyclerView.apply {
                adapter = HomeRecentRecyclerViewAdapter(mContext)
                        .apply { expenditures = MockData.get().toMutableList() } // TEST
                layoutManager = LinearLayoutManager(mContext)
            }

            homeMonthMoreInfoTextView.setOnClickListener { mViewModel.fetchMonthOverview() }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.viewModel = mViewModel
    }

    companion object {
        fun newInstance(): HomeFragment = HomeFragment()
    }
}
