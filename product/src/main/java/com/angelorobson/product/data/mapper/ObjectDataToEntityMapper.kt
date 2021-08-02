package com.angelorobson.product.data.mapper

import com.angelorobson.core.utils.Mapper
import com.angelorobson.db.features.product.entities.ProductEntity
import com.angelorobson.product.data.model.ProductData

class ObjectDataToEntityMapper : Mapper<ProductData, ProductEntity> {

    override fun map(source: ProductData) = ProductEntity(
        id = source.id,
        name = source.name,
        description = source.description,
        barcode = source.barcode,
        price = source.price,
        isActive = true
    )


}