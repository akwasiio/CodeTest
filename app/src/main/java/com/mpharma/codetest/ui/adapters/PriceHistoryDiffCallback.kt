package com.mpharma.codetest.ui.adapters

import androidx.recyclerview.widget.DiffUtil
import com.mpharma.codetest.domain.model.Price

class PriceHistoryDiffCallback (private val oldList: List<Price>, private val newList: List<Price>): DiffUtil.Callback(){
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]

        return oldItem.price == newItem.price
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]

        return oldItem.price == newItem.price && oldItem.date == newItem.date
    }
}