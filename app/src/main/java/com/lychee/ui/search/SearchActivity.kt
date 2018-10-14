package com.lychee.ui.search

import android.os.Bundle
import com.lychee.R
import com.lychee.databinding.ActivitySearchBinding
import com.lychee.ui.base.ui.BaseActivity

class SearchActivity : BaseActivity<ActivitySearchBinding, SearchViewModel>() {

    override val layoutResId: Int
        get() = R.layout.activity_search

    override val viewModelClass: Class<SearchViewModel>
        get() = SearchViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
}
