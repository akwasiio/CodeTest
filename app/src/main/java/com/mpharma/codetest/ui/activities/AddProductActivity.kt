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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddProductBinding.inflate(layoutInflater)

        setContentView(binding.root)

        with(binding) {
            addProductButton.setOnClickListener {
                val productName = productNameEditText.text.toString()
                val price = priceEditText.text.toString().toDouble()
                viewModel.addProduct(productName, price)

                priceEditText.text!!.clear()
                productNameEditText.text!!.clear()
                productNameEditText.requestFocus()
            }

            productNameEditText.addTextChangedListener {
                viewModel.setProductName(it.toString())
            }

            priceEditText.addTextChangedListener {
                viewModel.setPrice(it.toString())
            }
        }

        lifecycleScope.launch {
            viewModel.isAddProductEnabled.collect { isEnabled ->
                binding.addProductButton.isEnabled = isEnabled
                binding.addProductButton.alpha = if (isEnabled) 1f else 0.5f
            }
        }
    }
}