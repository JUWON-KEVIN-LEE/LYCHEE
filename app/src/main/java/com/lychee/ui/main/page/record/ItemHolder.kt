package com.lychee.ui.main.page.record

import android.support.v7.widget.RecyclerView
import com.lychee.BR
import com.lychee.data.model.Expenditure
import com.lychee.databinding.RecordListLayoutBinding

class ItemHolder : RecyclerView.ViewHolder {

    lateinit var expenditure: Expenditure

    private val binding : RecordListLayoutBinding

    constructor(binding : RecordListLayoutBinding, listener : RecordExpenditureClickListener) :super(binding.root){


        this.binding = binding

        binding.root.setOnClickListener { listener.onClick(expenditure) }

    }


    fun bindItems(item: Expenditure){

        with(binding){
            setVariable(BR.expenditure,item)
            executePendingBindings()
        }

        expenditure = item

    }
}