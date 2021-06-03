package com.mpharma.codetest.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "prices",
    foreignKeys = [ForeignKey(
        entity = ProductEntity::class,
        parentColumns = ["id"],
        childColumns = ["productId"],
        onDelete = ForeignKey.NO_ACTION,
        onUpdate = ForeignKey.CASCADE
    )]
)
data class PriceEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val price: Double,
    val date: String,
    val productId: Long,
) {
    constructor(price: Double, date: String, productId: Long) : this(0L, price, date, productId)
}