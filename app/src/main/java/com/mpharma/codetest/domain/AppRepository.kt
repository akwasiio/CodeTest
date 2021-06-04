package com.mpharma.codetest.domain

import androidx.room.withTransaction
import com.mpharma.codetest.data.api.ApiDataSource
import com.mpharma.codetest.data.local.ProductsDatabase
import com.mpharma.codetest.data.local.dao.ProductsDao
import com.mpharma.codetest.data.local.entities.PriceEntity
import com.mpharma.codetest.data.local.entities.ProductEntity
import com.mpharma.codetest.domain.mappers.EntityToPriceMapper
import com.mpharma.codetest.domain.mappers.EntityToProductMapper
import com.mpharma.codetest.domain.mappers.PriceToEntityMapper
import com.mpharma.codetest.domain.mappers.ProductToEntityMapper
import com.mpharma.codetest.domain.model.Price
import com.mpharma.codetest.domain.model.Product
import com.mpharma.codetest.domain.model.ProductAndPrices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

interface AppRepository {
    suspend fun getProductsWithPrices(): Flow<List<ProductAndPrices>>

    suspend fun addNewProduct(productName: String, price: Double)

    suspend fun addNewPriceToProduct(price: Price)

    suspend fun addNewPricesToProduct(prices: List<Price>)

    suspend fun deleteProductBy(id: String)

    suspend fun updateProduct(product: Product)

//    suspend fun fetchProductsFromServer()

}

@Singleton
class AppRepositoryImpl @Inject constructor(
    private val apiDataSource: ApiDataSource,
    private val productsDao: ProductsDao,
    private val database: ProductsDatabase,
    private val entityToPriceMapper: EntityToPriceMapper,
    private val entityToProductMapper: EntityToProductMapper,
    private val priceToEntityMapper: PriceToEntityMapper,
    private val productToEntityMapper: ProductToEntityMapper
) : AppRepository {
    override suspend fun getProductsWithPrices(): Flow<List<ProductAndPrices>> = flow {
        productsDao.getProducts().collect { entities ->
            val productsAndPrices = entities.map {
                ProductAndPrices(
                    product = entityToProductMapper.map(it.product),
                    prices = entityToPriceMapper.mapInputList(it.prices)
                )
            }

            if (productsAndPrices.isEmpty()) {
                fetchProductsFromServer()
            }

            emit(productsAndPrices)
        }

    }

    override suspend fun addNewProduct(productName: String, price: Double) {
        val productEntity = productToEntityMapper.map(Product(name = productName))

        productsDao.insertProduct(productToEntityMapper.map(Product(name = productName)))
        addNewPriceToProduct(Price(price = price, date = "", productId = productEntity.id))
    }

    override suspend fun addNewPriceToProduct(price: Price) {
        productsDao.insertPrice(priceToEntityMapper.map(price))
    }

    override suspend fun addNewPricesToProduct(prices: List<Price>) {
        productsDao.insertPrices(priceToEntityMapper.mapInputList(prices))
    }

    override suspend fun deleteProductBy(id: String) {
        productsDao.deleteProductBy(id)
    }

    override suspend fun updateProduct(product: Product) {
        // TODO: UPDATE PRODUCT
    }

    private suspend fun fetchProductsFromServer() = withContext(Dispatchers.IO) {
        apiDataSource.fetchProducts().collect { products ->
            database.withTransaction {
                products.forEach { product ->
                    val productEntity = ProductEntity(name = product.name)
                    productsDao.insertProduct(productEntity)

                    val priceEntities = product.prices.map {
                        PriceEntity(
                            price = it.price,
                            date = it.date,
                            productId = productEntity.id
                        )
                    }

                    productsDao.insertPrices(priceEntities)
                }
            }
        }

    }

}