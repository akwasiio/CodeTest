package com.mpharma.codetest.ui.screenstates

import com.mpharma.codetest.domain.model.ProductAndPrices

sealed class ProductDetailState {
    object Loading : ProductDetailState()

   data class Success(val data: ProductAndPrices) : ProductDetailState()
}