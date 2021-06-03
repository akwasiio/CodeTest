package com.mpharma.codetest.di

import android.content.Context
import androidx.room.Room
import com.mpharma.codetest.data.local.ProductsDatabase
import com.mpharma.codetest.data.local.dao.ProductsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): ProductsDatabase {
        return Room.databaseBuilder(context, ProductsDatabase::class.java, "products.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun providesProductsDao(database: ProductsDatabase): ProductsDao {
        return database.productsDao()
    }
}