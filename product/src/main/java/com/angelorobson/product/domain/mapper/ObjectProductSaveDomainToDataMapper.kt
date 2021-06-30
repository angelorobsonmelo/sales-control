package com.angelorobson.product.domain.mapper

import com.angelorobson.core.utils.Mapper
import com.angelorobson.product.data.model.ProductData
import com.angelorobson.product.data.model.ProductSaveData
import com.angelorobson.product.domain.model.ProductDomain
import com.angelorobson.product.domain.model.ProductSaveDomain

class ObjectProductSaveDomainToDataMapper : Mapper<ProductSaveDomain, ProductSaveData> {

    override fun map(source: ProductSaveDomain) = ProductSaveData(
        id = source.id,
        name = source.name,
        description = source.description,
        barcode = source.barcode,
        price = source.price
    )


}