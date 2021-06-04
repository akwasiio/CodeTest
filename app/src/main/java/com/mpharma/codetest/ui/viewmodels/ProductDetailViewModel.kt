package com.mpharma.codetest.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mpharma.codetest.domain.AppRepositoryImpl
import com.mpharma.codetest.ui.screenstates.ProductDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(private val appRepositoryImpl: AppRepositoryImpl) :
    ViewModel() {

    private val _state = MutableStateFlow<ProductDetailState>(ProductDetailState.Loading)
    val state: StateFlow<ProductDetailState> = _state

    fun getProductWithPrices(productId: String) {
        viewModelScope.launch {
            appRepositoryImpl.getProductWithPrices(productId).collect {
                _state.value = ProductDetailState.Success(data = it)
            }
        }
    }
}