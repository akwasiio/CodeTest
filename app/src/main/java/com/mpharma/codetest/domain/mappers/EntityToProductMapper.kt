package com.mpharma.codetest.domain.mappers

import com.mpharma.codetest.BaseMapper
import com.mpharma.codetest.data.local.entities.ProductEntity
import com.mpharma.codetest.domain.model.Product
import javax.inject.Inject

class EntityToProductMapper @Inject constructor(): BaseMapper<ProductEntity, Product> {
    override fun map(input: ProductEntity): Product {
        return Product(input.name)
    }

    override fun mapInputList(input: List<ProductEntity>): List<Product> {
        return input.map { map(it) }
    }
}