package com.mpharma.codetest.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    tableName = "prices",indices = [Index("productId")],
//    foreignKeys = [ForeignKey(
//        entity = ProductEntity::class,
//        parentColumns = ["id"],
//        childColumns = ["productId"],
//        onUpdate = ForeignKey.CASCADE,
//        onDelete = ForeignKey.CASCADE
//    ),
//    ]
)
data class PriceEntity(
    @PrimaryKey(autoGenerate = true) val priceId: Long,
    val price: Double,
    val date: Date,
    val productId: String,
) {
    constructor(price: Double, date: Date, productId: String) : this(0L, price, date, productId)
}