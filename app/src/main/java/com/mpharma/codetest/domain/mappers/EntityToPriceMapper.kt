package com.mpharma.codetest.domain.mappers

import com.mpharma.codetest.BaseMapper
import com.mpharma.codetest.data.local.entities.PriceEntity
import com.mpharma.codetest.domain.model.Price
import javax.inject.Inject

class EntityToPriceMapper @Inject constructor() : BaseMapper<PriceEntity, Price> {
    override fun map(input: PriceEntity): Price {
        return Price(price = input.price, date = input.date, productId = input.productId)
    }

    override fun mapInputList(input: List<PriceEntity>): List<Price> {
        return input.map { map(it) }
    }
}