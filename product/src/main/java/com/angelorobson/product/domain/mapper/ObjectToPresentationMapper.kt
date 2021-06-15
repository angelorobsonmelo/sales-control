package com.angelorobson.product.domain.mapper

import com.angelorobson.core.utils.Mapper
import com.angelorobson.product.domain.model.ProductDomain
import com.angelorobson.product.presentation.model.ProductPresentation

class ObjectToPresentationMapper : Mapper<ProductDomain, ProductPresentation> {

    override fun map(source: ProductDomain) = ProductPresentation(
        id = source.id,
        name = source.name,
        description = source.description,
        barcode = source.barcode,
        price = source.price
    )


}