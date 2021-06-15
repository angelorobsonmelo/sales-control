package com.angelorobson.product.data.mapper

import com.angelorobson.core.utils.Mapper
import com.angelorobson.db.features.product.entities.ProductEntity
import com.angelorobson.product.domain.model.ProductDomain

class ObjectToDomainMapper : Mapper<ProductEntity, ProductDomain> {

    override fun map(source: ProductEntity) = ProductDomain(
        id = source.id,
        name = source.name,
        description = source.description,
        barcode = source.barcode,
        price = source.price
    )


}