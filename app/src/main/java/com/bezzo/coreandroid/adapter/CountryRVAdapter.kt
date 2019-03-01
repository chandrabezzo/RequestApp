package com.bezzo.coreandroid.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bezzo.core.base.BaseHolder
import com.bezzo.core.data.model.Country
import com.bezzo.coreandroid.R
import kotlinx.android.synthetic.main.item_rv_sample.view.*

/**
 * Created by bezzo on 11/01/18.
 * Change String to model you need convert to recycler view
 */
class CountryRVAdapter(var context : Context,
                       var list : ArrayList<Country>)
    : RecyclerView.Adapter<CountryRVAdapter.Item>() {

    // uncomment if you use click listener
//    lateinit var listener : OnItemClickListener
//
//    fun setOnItemClickListener(listener: OnItemClickListener){
//        this.listener = listener
//    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: Item, position: Int) {
        holder.model = list[position]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Item {
        return Item(LayoutInflater.from(parent.context)
                .inflate(R.layout.item_rv_sample, parent, false))
    }


    inner class Item(itemView : View) : BaseHolder<Country>(itemView){

        init {
            // add listener if you need
//            itemView.setOnClickListener {
//                listener.onItemClick(it, layoutPosition)
//            }
//
//            itemView.setOnLongClickListener {
//                listener.onItemLongClick(it, layoutPosition)
//            }
        }

        override fun setContent(model: Country) {
            itemView.tv_item_rv.text = model.name
        }
    }
}