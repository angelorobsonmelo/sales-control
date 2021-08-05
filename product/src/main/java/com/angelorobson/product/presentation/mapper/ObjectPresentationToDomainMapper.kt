package com.angelorobson.product.presentation.mapper

import com.angelorobson.core.utils.Mapper
import com.angelorobson.product.domain.model.ProductDomain
import com.angelorobson.product.presentation.model.ProductPresentation

class ObjectPresentationToDomainMapper : Mapper<ProductPresentation, ProductDomain> {

    override fun map(source: ProductPresentation) = ProductDomain(
        id = source.id,
        name = source.name,
        description = source.description,
        barcode = source.barcode,
        price = source.price,
        isActive = source.isActive
    )


}