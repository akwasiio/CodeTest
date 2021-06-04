package com.mpharma.codetest.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mpharma.codetest.R
import com.mpharma.codetest.databinding.ItemProductLayoutBinding
import com.mpharma.codetest.domain.model.ProductAndPrices

class ProductsAdapter(
    private val onClick: (ProductAndPrices) -> Unit,
    private val onDeleteHandler: (String) -> Unit
) :
    RecyclerView.Adapter<ProductsViewHolder>() {
    private val items = mutableListOf<ProductAndPrices>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemProductLayoutBinding.inflate(layoutInflater, parent, false)

        return ProductsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        holder.bind(items[position], onClick, onDeleteHandler)
    }

    override fun getItemCount(): Int = items.size

    fun setData(products: List<ProductAndPrices>) {
        val diffCallback = ProductsDiffCallback(items, products)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        items.clear()
        items.addAll(products)
        diffResult.dispatchUpdatesTo(this)
    }
}

class ProductsViewHolder(private val binding: ItemProductLayoutBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        item: ProductAndPrices,
        onClickHandler: (ProductAndPrices) -> Unit,
        onDeleteHandler: (String) -> Unit
    ) {
        with(binding) {
            productNameTextView.text = item.product.name
            latestPriceTextView.text = binding.root.resources.getString(R.string.amount, item.prices.first().price.toString())

            root.setOnClickListener { onClickHandler(item) }
            deleteIcon.setOnClickListener { onDeleteHandler(item.product.id) }
        }
    }
}