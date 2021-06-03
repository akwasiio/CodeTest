package com.mpharma.codetest.kotlin.api

import com.google.common.truth.Truth.assertThat
import com.mpharma.codetest.data.api.ApiDataSource
import com.mpharma.codetest.data.api.ApiService
import com.mpharma.codetest.kotlin.buildApiService
import com.mpharma.codetest.kotlin.buildOkHttpClient
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test

class ApiDataSourceTests {
    private lateinit var mockWebServer: MockWebServer
    private lateinit var apiService: ApiService

    private lateinit var loggingInterceptor: HttpLoggingInterceptor
    private lateinit var okHttpClient: OkHttpClient

    private lateinit var apiDataSource: ApiDataSource

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.dispatcher = ApiServiceDispatcher()
        mockWebServer.start()
        loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        okHttpClient = buildOkHttpClient(loggingInterceptor)
        apiService = buildApiService(mockWebServer, okHttpClient)

        apiDataSource = ApiDataSource(apiService)
    }

    @After
    fun shutdown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `check that products are returned when api call is made`() = runBlocking {
        val productsFlow = apiDataSource.fetchProducts()

        productsFlow.collect { products ->
            assertThat(products.size).isAtLeast(1)
        }
    }

}