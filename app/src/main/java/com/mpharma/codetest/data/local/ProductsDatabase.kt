package com.mpharma.codetest.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mpharma.codetest.data.local.dao.ProductsDao
import com.mpharma.codetest.data.local.entities.PriceEntity
import com.mpharma.codetest.data.local.entities.ProductEntity

@Database(version = 1, entities = [PriceEntity::class, ProductEntity::class], exportSchema = false)
abstract class ProductsDatabase : RoomDatabase() {
    abstract fun productsDao(): ProductsDao
}