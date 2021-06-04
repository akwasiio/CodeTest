package com.mpharma.codetest.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mpharma.codetest.R
import com.mpharma.codetest.databinding.ItemPriceHistoryLayoutBinding
import com.mpharma.codetest.domain.model.Price
import com.mpharma.codetest.format

class PriceHistoryAdapter : RecyclerView.Adapter<PriceHistoryViewHolder>() {
    private val items = mutableListOf<Price>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PriceHistoryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemPriceHistoryLayoutBinding.inflate(layoutInflater, parent, false)

        return PriceHistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PriceHistoryViewHolder, position: Int) {
       holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setData(prices: List<Price>) {
        val diffCallback = PriceHistoryDiffCallback(items, prices)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        items.clear()
        items.addAll(prices)
        diffResult.dispatchUpdatesTo(this)
    }

}

class PriceHistoryViewHolder(private val binding: ItemPriceHistoryLayoutBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Price) {
        with(binding) {
            dateTextView.text = item.date.format()
            priceTextView.text = binding.root.resources.getString(R.string.amount, item.price.toString())
        }
    }
}