package com.mpharma.codetest.kotlin.room

import android.content.Context
import androidx.room.Room
import androidx.room.withTransaction
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.mpharma.codetest.kotlin.DummyData
import com.mpharma.codetest.data.local.ProductsDatabase
import com.mpharma.codetest.data.local.dao.ProductsDao
import com.mpharma.codetest.data.local.entities.PriceEntity
import com.mpharma.codetest.data.local.entities.ProductEntity
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class ProductsTest {
    private lateinit var productsDao: ProductsDao
    private lateinit var database: ProductsDatabase

    @Before
    fun createDatabase() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, ProductsDatabase::class.java).build()
        productsDao = database.productsDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDatabase() {
        database.close()
    }

    @Test
    fun `check that product gets saved to database`() = runBlocking {
        val product = DummyData.productEntity

        productsDao.insertProduct(product)
        val products = productsDao.products()

        assertThat(products).isNotEmpty()
    }

    @Test
    fun `check that product gets saved with price`() = runBlocking {
        val productsFromApi = DummyData.productModel
        database.withTransaction {
            productsFromApi.forEach { product ->
                val productEntity = ProductEntity(name = product.name)

                productsDao.insertProduct(productEntity)
                val prices = product.prices.map {
                    PriceEntity(price = it.price, date = it.date, productId = productEntity.id)
                }
                productsDao.insertPrices(prices)
            }
        }

        val productsAndPrices = productsDao.getProducts().first()

        assertThat(productsAndPrices).isNotNull()
        assertThat(productsAndPrices).isNotEmpty()
        assertThat(productsAndPrices.first().product.name).isEqualTo(productsFromApi.first().name)
    }

    @Test
    fun `given an id, return a product with its price list`() = runBlocking {
        val productEntity = DummyData.productEntity
        productsDao.insertProduct(productEntity)

        val price = DummyData.priceEntity.copy(productId = productEntity.id)
        val prices = listOf(price, price.copy(price = 100.0), price.copy(price = 60.0))
        productsDao.insertPrices(prices)

        val productWithPrices = productsDao.getProductWithPrices(productEntity.id)

        assertThat(productWithPrices.product.id).isEqualTo(productEntity.id)
        assertThat(productWithPrices.product.name).isEqualTo(productEntity.name)
        assertThat(productWithPrices.prices.size).isEqualTo(prices.size)
    }

    @Test
    fun `check that deleting product does not delete price`() = runBlocking {
        val productEntity = DummyData.productEntity
        productsDao.insertProduct(productEntity)

        val price = DummyData.priceEntity.copy(productId = productEntity.id)
        productsDao.insertPrices(listOf(price))

        // delete product
        productsDao.deleteProductBy(productEntity.id)

        val prices = productsDao.getPricesForProduct(productEntity.id)

        assertThat(prices).isNotNull()
        assertThat(prices).isNotEmpty()
        assertThat(prices.size).isEqualTo(1)
    }
}