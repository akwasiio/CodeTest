package com.mpharma.codetest.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mpharma.codetest.domain.AppRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddProductActivityViewModel @Inject constructor(private val appRepositoryImpl: AppRepositoryImpl) :
    ViewModel() {

    private val productNameFlow = MutableStateFlow("")
    private val priceFlow = MutableStateFlow("")


    fun addProduct(productName: String, price: Double) {
        viewModelScope.launch {
            appRepositoryImpl.addNewProduct(productName, price)
        }
    }

    fun setProductName(name: String) {
        productNameFlow.value = name
    }

    fun setPrice(price: String) {
        priceFlow.value = price
    }

    val isAddProductEnabled: Flow<Boolean> = combine(productNameFlow, priceFlow) { product, price ->
        return@combine product.isNotEmpty() && price.isNotEmpty()
    }

    fun updateExistingProduct(
        productId: String,
        productName: String,
        price: Double,
        withNewPrice: Boolean
    ) {
        viewModelScope.launch {
            appRepositoryImpl.updateProduct(productId, productName, price, withNewPrice)
        }
    }
}