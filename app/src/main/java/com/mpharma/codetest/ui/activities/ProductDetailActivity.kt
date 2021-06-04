package com.mpharma.codetest.ui.activities

import android.content.Intent
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
    private lateinit var productName: String
    private var latestPrice: Double? = null


    private val productDetailViewModel: ProductDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)

        setContentView(binding.root)

        supportActionBar?.title = "Product Details"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)


        productId = intent.getStringExtra("product_id") ?: return
        productDetailViewModel.getProductWithPrices(productId)

        setupAdapterAndRecyclerView()
        observeScreenState()
        setupFabClickListener()

    }

    private fun setupAdapterAndRecyclerView(){
        adapter = PriceHistoryAdapter()
        binding.priceHistoryRecyclerView.adapter = adapter
    }

    private fun observeScreenState(){
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
                    productName = state.data.product.name
                    latestPrice = state.data.prices.first().price

                    productNameTextView.text = productName
                    adapter.setData(state.data.prices)
                }
            }

            else -> {}
        }
    }

    private fun setupFabClickListener() {
        binding.fab.setOnClickListener {
            if (latestPrice != null) {
                openProductFormActivity()
            }
        }
    }

    private fun openProductFormActivity() {
        val intent = Intent(this, AddProductActivity::class.java)
        intent.putExtra("product_id", productId)
        intent.putExtra("product_name", productName)
        intent.putExtra("price", latestPrice!!)

        startActivity(intent)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}