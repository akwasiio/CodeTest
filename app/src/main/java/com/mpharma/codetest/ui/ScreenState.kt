package com.mpharma.codetest.ui

import com.mpharma.codetest.domain.model.ProductAndPrices


sealed class ScreenState {
    object Loading : ScreenState()
    data class Error(val message: String): ScreenState()
    data class Success(val data: List<ProductAndPrices>) : ScreenState()
}