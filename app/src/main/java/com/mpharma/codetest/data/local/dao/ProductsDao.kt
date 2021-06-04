package com.mpharma.codetest.data.local.dao

import androidx.room.*
import com.mpharma.codetest.data.local.entities.PriceEntity
import com.mpharma.codetest.data.local.entities.ProductAndPricesEntity
import com.mpharma.codetest.data.local.entities.ProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductsDao {
    @Insert
    suspend fun insertProducts(products: List<ProductEntity>): List<Long>

    @Insert
    suspend fun insertProduct(product: ProductEntity): Long

    @Insert
    suspend fun insertPrices(price: List<PriceEntity>)

    @Insert
    suspend fun insertPrice(price: PriceEntity)

    @Query("SELECT * FROM products")
    suspend fun products(): List<ProductEntity>

    @Transaction
    @Query("SELECT * FROM products")
    fun getProducts(): Flow<List<ProductAndPricesEntity>>

    @Query("DELETE FROM products WHERE id = :id")
    suspend fun deleteProductBy(id: String)

    @Query("SELECT * FROM prices WHERE productId = :productId")
    suspend fun getPricesForProduct(productId: String): List<PriceEntity>

    @Query("SELECT * FROM products WHERE id = :productId")
    suspend fun getProductWithPrices(productId: String): ProductAndPricesEntity
}