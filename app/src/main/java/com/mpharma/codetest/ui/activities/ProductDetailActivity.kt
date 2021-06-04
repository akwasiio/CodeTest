package com.mpharma.codetest.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.mpharma.codetest.databinding.ActivityProductDetailBinding
import com.mpharma.codetest.ui.adapters.PriceHistoryAdapter
import com.mpharma.codetest.ui.screenstates.ProductDetailState
import com.mpharma.codetest.ui.viewmodels.ProductDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductDetailBinding
    private lateinit var productId: String
    private lateinit var adapter: PriceHistoryAdapter

    private val productDetailViewModel: ProductDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)

        setContentView(binding.root)

        productId = intent.getStringExtra("product_id") ?: return
        adapter = PriceHistoryAdapter()
        binding.priceHistoryRecyclerView.adapter = adapter

        productDetailViewModel.getProductWithPrices(productId)

        lifecycleScope.launch {
            productDetailViewModel.state.collect { screenState ->
                handleScreenState(screenState)
            }
        }


    }

    private fun handleScreenState(state: ProductDetailState) {
        when (state) {
            is ProductDetailState.Success -> {
                with(binding) {

                    productNameTextView.text = state.data.product.name
                    adapter.setData(state.data.prices)
                }
            }

            else -> {}
        }
    }
}