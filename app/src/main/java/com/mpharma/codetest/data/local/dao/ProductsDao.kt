package com.mpharma.codetest.data.local.dao

import androidx.room.*
import com.mpharma.codetest.data.local.entities.PriceEntity
import com.mpharma.codetest.data.local.entities.ProductAndPrices
import com.mpharma.codetest.data.local.entities.ProductEntity

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
    suspend fun getProducts(): List<ProductAndPrices>

    @Delete
    suspend fun deleteProduct(product: ProductEntity)

    @Query("SELECT * FROM prices WHERE productId = :productId")
    suspend fun getPricesForProduct(productId: Long): List<PriceEntity>
}