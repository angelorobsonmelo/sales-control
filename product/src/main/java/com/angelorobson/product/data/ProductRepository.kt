package com.angelorobson.product.data

import com.angelorobson.product.data.model.ProductData
import com.angelorobson.product.presentation.model.ProductPresentation

interface ProductRepository {

    suspend fun insert(products: List<ProductData>)
    suspend fun insert(product: ProductData): Long
    suspend fun getAll(): List<ProductData>
    suspend fun findByBarcode(barcode: String): ProductData
    suspend fun findById(id: Int): ProductData
    suspend fun findByName(name: String): ProductData
}