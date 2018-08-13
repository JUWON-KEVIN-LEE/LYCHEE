package com.lychee.ui.main.page.record

import android.databinding.DataBindingUtil
import android.provider.ContactsContract
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lychee.BR
import com.lychee.R
import com.lychee.data.model.Expenditure
import com.lychee.databinding.RecordHeaderLayoutBinding
import com.lychee.databinding.RecordListLayoutBinding
import com.lychee.mock.MockData
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.fragment_record.view.*

/**
 * Created by user on 2018-08-09.
 */
class RecyclerAdapter(val itemList:List<Expenditure>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private val TYPE_HEADER : Int  = 0;
    private val TYPE_ITEM : Int = 1


    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder:RecyclerView.ViewHolder, position: Int) {

        if(position == 0){

        }else{
            //ItemHolder.bindItems(itemList[position])
        }



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        //if(viewType == TYPE_HEADER)
        val headerBinding = DataBindingUtil.inflate<RecordHeaderLayoutBinding>(
                LayoutInflater.from(parent.context),
                R.layout.record_header_layout,
                parent,
                false
        )
        return HeaderHolder(headerBinding)
        /*}else if(viewType == TYPE_ITEM){
            val itemBinding = DataBindingUtil.inflate<RecordListLayoutBinding>(
                    LayoutInflater.from(parent.context),
                    R.layout.record_list_layout,
                    parent,
                    false
            )
            return ItemHolder(itemBinding)
        }*/

    }

    class ItemHolder(private val binding : RecordListLayoutBinding) :RecyclerView.ViewHolder(binding.root) {



        fun bindItems(item:Expenditure){

            with(binding){
                setVariable(BR.expenditure,item)
                executePendingBindings()

            }



        }
    }

    class HeaderHolder(private val binding : RecordHeaderLayoutBinding) : RecyclerView.ViewHolder(binding.root){




    }


}