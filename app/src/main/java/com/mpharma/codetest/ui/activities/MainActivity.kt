package com.mpharma.codetest.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.mpharma.codetest.databinding.ActivityMainBinding
import com.mpharma.codetest.ui.ScreenState
import com.mpharma.codetest.ui.adapters.ProductsAdapter
import com.mpharma.codetest.ui.viewmodels.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val mainViewModel: MainActivityViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ProductsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        initAdapter()

        lifecycleScope.launchWhenStarted {
            mainViewModel.state.collect { screenState ->
                handleScreenState(screenState)
            }
        }

    }

    private fun initAdapter() {
        adapter = ProductsAdapter {
            Log.e("Clicked", "Product ${it.product.name} has been clicked")
        }

        with(binding) {
            productsRecyclerView.adapter = adapter
            productsRecyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    private fun handleScreenState(state: ScreenState) {
        when(state) {
            is ScreenState.Success -> {
                adapter.setData(state.data)
            }

            is ScreenState.Loading -> {
                // TODO: SHOW PROGRESS BAR
            }

            is ScreenState.Error -> {
                // TODO: SHOW ERROR
            }
        }
    }
}