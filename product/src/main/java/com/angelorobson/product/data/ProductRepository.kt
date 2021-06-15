package com.angelorobson.product.data

import com.angelorobson.product.presentation.model.ProductPresentation

interface ProductRepository {

    suspend fun insert(products: List<ProductPresentation>)
    suspend fun insert(product: ProductPresentation)
    suspend fun getAll(): List<ProductPresentation>
    suspend fun findByBarcode(barcode: String): ProductPresentation
    suspend fun findById(id: Int): ProductPresentation
    suspend fun findByName(name: String): ProductPresentation
}