package com.angelorobson.product.data

import com.angelorobson.product.data.model.ProductData
import com.angelorobson.product.data.model.ProductSaveData
import com.angelorobson.product.presentation.model.ProductPresentation

interface ProductRepository {

    suspend fun insert(products: List<ProductData>)
    suspend fun insert(product: ProductSaveData): Long
    suspend fun getAll(): List<ProductData>
    suspend fun findByBarcode(barcode: String): List<ProductData>
    suspend fun findById(id: Long): ProductData
    suspend fun findByName(name: String): ProductData
    suspend fun findByTerm(name: String): List<ProductData>
    suspend  fun update(product: ProductSaveData)
    suspend fun inactivateProduct(product: ProductData)

}