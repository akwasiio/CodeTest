package com.mpharma.codetest.domain.mappers

import com.mpharma.codetest.BaseMapper
import com.mpharma.codetest.data.local.entities.PriceEntity
import com.mpharma.codetest.domain.model.Price
import javax.inject.Inject

class PriceToEntityMapper @Inject constructor() : BaseMapper<Price, PriceEntity> {
    override fun map(input: Price): PriceEntity {
        return PriceEntity(price = input.price, date = input.date, productId = input.productId)
    }

    override fun mapInputList(input: List<Price>): List<PriceEntity> {
        return input.map { map(it) }
    }
}