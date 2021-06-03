package com.mpharma.codetest.data.api

import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ApiDataSource @Inject constructor(private val apiService: ApiService) {
    suspend fun fetchProducts() = flow {
        emit(apiService.getProducts().products)
    }
}