package com.mpharma.codetest.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import com.mpharma.codetest.databinding.ActivityAddProductBinding
import com.mpharma.codetest.ui.viewmodels.AddProductActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddProductActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddProductBinding
    private val viewModel: AddProductActivityViewModel by viewModels()

    private var productId: String? = null
    private var productName: String? = null
    private var latestPrice: Double? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddProductBinding.inflate(layoutInflater)

        setContentView(binding.root)

        if(intent.hasExtra("product_id")) {
            productId = intent.getStringExtra("product_id")
            productName = intent.getStringExtra("product_name")
            latestPrice = intent.getDoubleExtra("price", 0.0)

            binding.priceEditText.setText(latestPrice.toString())
            binding.productNameEditText.setText(productName)
            viewModel.setProductName(productName!!)
            viewModel.setPrice(latestPrice.toString())
        }

        observeTextFields()
        setupButtonClickListener()
        observeButton()
    }

    private fun observeTextFields() {
        with(binding) {
            productNameEditText.addTextChangedListener {
                viewModel.setProductName(it.toString())
            }

            priceEditText.addTextChangedListener {
                viewModel.setPrice(it.toString())
            }
        }
    }

    private fun setupButtonClickListener() {
        with(binding) {
            binding.addProductButton.setOnClickListener {
                val productName = productNameEditText.text.toString()
                val price = priceEditText.text.toString().toDouble()

                if (productId == null) {
                    addNewProduct(productName, price)
                } else {
                    updateExistingProduct(productName, price)
                }

                priceEditText.text!!.clear()
                productNameEditText.text!!.clear()
                productNameEditText.requestFocus()
            }
        }
    }

    private fun observeButton() {
        lifecycleScope.launch {
            viewModel.isAddProductEnabled.collect { isEnabled ->
                binding.addProductButton.isEnabled = isEnabled
                binding.addProductButton.alpha = if (isEnabled) 1f else 0.5f
            }
        }
    }

    private fun addNewProduct(productName: String, price: Double) {
        viewModel.addProduct(productName, price)
    }

    private fun updateExistingProduct(productName: String, price: Double) {
        viewModel.updateExistingProduct(productId!!, productName, price, withNewPrice = price != latestPrice!!)
    }
}