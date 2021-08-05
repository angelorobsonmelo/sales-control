package com.angelorobson.product.domain.mapper

import com.angelorobson.core.utils.Mapper
import com.angelorobson.product.data.model.ProductData
import com.angelorobson.product.domain.model.ProductDomain

class ObjectDomainToDataMapper : Mapper<ProductDomain, ProductData> {

    override fun map(source: ProductDomain) = ProductData(
        id = source.id,
        name = source.name,
        description = source.description,
        barcode = source.barcode,
        price = source.price,
        isActive = source.isActive
    )


}