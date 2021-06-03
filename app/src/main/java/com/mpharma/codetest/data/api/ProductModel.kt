package com.mpharma.codetest.data.api

data class ProductModel(val name: String, val prices: List<PriceModel>)

data class ProductResponse(val products: List<ProductModel>)