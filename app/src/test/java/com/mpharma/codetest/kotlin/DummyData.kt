package com.mpharma.codetest.kotlin

import com.mpharma.codetest.data.api.PriceModel
import com.mpharma.codetest.data.api.ProductModel
import com.mpharma.codetest.data.local.entities.PriceEntity
import com.mpharma.codetest.data.local.entities.ProductEntity

internal object DummyData {
    val productEntity = ProductEntity(name = "Paracetamol")

    val priceEntity = PriceEntity(price = 19.0, date = "", productId = 1)

    val products = listOf(
        ProductEntity(name = "Paracetamol"),
        ProductEntity("Exforge 10mg"),
        ProductEntity("Exforge 20mg")
    )

    val productModel = listOf(
        ProductModel(
            "Paracetamol",
            listOf(
                PriceModel(1, 12.0, ""),
                PriceModel(2, 12.0, ""),
                PriceModel(3, 12.0, ""),
                PriceModel(4, 12.0, "")
            )
        ),
        ProductModel(
            "ORS",
            listOf(
                PriceModel(1, 12.0, ""),
                PriceModel(2, 12.0, ""),
                PriceModel(3, 12.0, ""),
                PriceModel(4, 12.0, "")
            )
        ),
        ProductModel(
            "Vitamins",
            listOf(
                PriceModel(1, 12.0, ""),
                PriceModel(2, 12.0, ""),
                PriceModel(3, 12.0, ""),
                PriceModel(4, 12.0, "")
            )
        )
    )
}