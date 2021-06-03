package com.mpharma.codetest.room

import android.content.Context
import androidx.room.Room
import androidx.room.withTransaction
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.mpharma.codetest.DummyData
import com.mpharma.codetest.data.local.ProductsDatabase
import com.mpharma.codetest.data.local.dao.ProductsDao
import com.mpharma.codetest.data.local.entities.PriceEntity
import com.mpharma.codetest.data.local.entities.ProductEntity
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
                val productId = productsDao.insertProduct(ProductEntity(name = product.name))
                val prices = product.prices.map {
                    PriceEntity(price = it.price, date = it.date, productId = productId)
                }
                productsDao.insertPrices(prices)
            }
        }
        val productsAndPrices = productsDao.getProducts()
        assertThat(productsAndPrices).isNotNull()
        assertThat(productsAndPrices).isNotEmpty()
        assertThat(productsAndPrices.first().product.name).isEqualTo(productsFromApi.first().name)
    }

    @Test
    fun `check that deleting product does not delete price`() = runBlocking {
        val product = DummyData.productEntity
        val productId = productsDao.insertProduct(product)

        val price = DummyData.priceEntity.copy(productId = productId)
        productsDao.insertPrices(listOf(price))

        // delete product
        productsDao.deleteProduct(product)

        val prices = productsDao.getPricesForProduct(productId)

        assertThat(prices).isNotNull()
        assertThat(prices).isNotEmpty()
        assertThat(prices.size).isEqualTo(1)
    }
}