package com.mpharma.codetest.domain.mappers

import com.mpharma.codetest.BaseMapper
import com.mpharma.codetest.data.local.entities.ProductEntity
import com.mpharma.codetest.domain.model.Product
import javax.inject.Inject

class ProductToEntityMapper @Inject constructor() : BaseMapper<Product, ProductEntity>{
    override fun map(input: Product): ProductEntity {
        return ProductEntity(name = input.name)
    }

    override fun mapInputList(input: List<Product>): List<ProductEntity> {
        return input.map { ProductEntity(it.name) }
    }
}