package com.lychee.ui.cardlist

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import com.lychee.R
import com.lychee.mock.MockData
import com.lychee.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_card_list.*

/**
 * TODO
 * REFACTORING
 * 카드 이미지의 경우 해상도가 좋지 않은 경우가 많다.
 * 편집에 대한 처리
 */
class CardListActivity : BaseActivity<CardListViewModel>(R.layout.activity_card_list) {

    override val viewModelClass: Class<CardListViewModel>
        get() = CardListViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        back_button.setOnClickListener { finish() }

        card_view_pager.apply { adapter = CardListAdapter(this@CardListActivity, MockData.get_()) }

        edit.setOnClickListener {
            card_view_pager.apply {
                val adapter = adapter as CardListAdapter
                if(adapter.editMode) {
                    (it as TextView).text = "편집"
                    adapter.editMode = false
                } else {
                    (it as TextView).text = "삭제"
                    adapter.editMode = true
                }

                invalidate()
            }
        }
    }

    companion object {
        fun start(context: Context) = context.startActivity(Intent(context, CardListActivity::class.java))
    }
}
