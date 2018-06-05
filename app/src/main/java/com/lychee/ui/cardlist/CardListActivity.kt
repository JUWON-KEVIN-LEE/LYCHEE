package com.lychee.ui.cardlist

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.lychee.R
import com.lychee.ui.base.BaseActivity

class CardListActivity : BaseActivity<CardListViewModel>(R.layout.activity_card_list) {

    override val viewModelClass: Class<CardListViewModel>
        get() = CardListViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    companion object {
        fun start(context: Context) = context.startActivity(Intent(context, CardListActivity::class.java))
    }
}
