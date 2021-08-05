package com.angelorobson.product.data.mapper

import com.angelorobson.core.utils.Mapper
import com.angelorobson.db.features.product.entities.ProductEntity
import com.angelorobson.product.data.model.ProductData
import com.angelorobson.product.domain.model.ProductDomain

class ObjectEntityToDataMapper : Mapper<ProductEntity, ProductData> {

    override fun map(source: ProductEntity) = ProductData(
        id = source.id,
        name = source.name,
        description = source.description,
        barcode = source.barcode,
        price = source.price,
        isActive = source.isActive
    )


}