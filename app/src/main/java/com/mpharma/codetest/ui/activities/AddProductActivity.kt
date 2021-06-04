package com.mpharma.codetest.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.mpharma.codetest.databinding.ActivityAddProductBinding

class AddProductActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddProductBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddProductBinding.inflate(layoutInflater)

        setContentView(binding.root)

        with(binding) {
            addProductButton.setOnClickListener {
                Log.e("Price", priceEditText.text.toString())
                Log.e("ProductName", productNameEditText.text.toString())
            }
        }
    }
}