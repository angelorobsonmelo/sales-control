package com.angelorobson.product.domain.mapper

import com.angelorobson.core.utils.Mapper
import com.angelorobson.product.data.model.ProductData
import com.angelorobson.product.domain.model.ProductDomain

class ObjectDataToDomainMapper : Mapper<ProductData, ProductDomain> {

    override fun map(source: ProductData) = ProductDomain(
        id = source.id,
        name = source.name,
        description = source.description,
        barcode = source.barcode,
        price = source.price,
        isActive = source.isActive
    )


}