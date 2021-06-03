package com.mpharma.codetest.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val name: String
) {
    constructor(name: String) : this(0L, name)
}
