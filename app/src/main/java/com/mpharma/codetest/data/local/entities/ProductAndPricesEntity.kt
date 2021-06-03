package com.mpharma.codetest.data.local.entities

import androidx.room.Embedded
import androidx.room.Relation

data class ProductAndPricesEntity(
    @Embedded val product: ProductEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "productId"
    ) val prices: List<PriceEntity>
)