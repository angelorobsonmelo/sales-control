package com.angelorobson.product.presentation.mapper

import com.angelorobson.core.utils.Mapper
import com.angelorobson.product.domain.model.ProductDomain
import com.angelorobson.product.domain.model.ProductSaveDomain
import com.angelorobson.product.presentation.model.ProductPresentation
import com.angelorobson.product.presentation.model.ProductToSavePresentation

class ObjectSaveProductPresentationToDomainMapper :
    Mapper<ProductToSavePresentation, ProductSaveDomain> {

    override fun map(source: ProductToSavePresentation) = ProductSaveDomain(
        id = source.id,
        name = source.name,
        description = source.description,
        barcode = source.barcode,
        price = source.price
    )


}