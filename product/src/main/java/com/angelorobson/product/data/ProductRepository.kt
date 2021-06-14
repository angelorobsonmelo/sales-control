package com.angelorobson.product.data

import com.angelorobson.db.features.product.entities.ProductEntity

interface ProductRepository {

    suspend fun insert(product: List<ProductEntity>)
    suspend fun insert(product: ProductEntity)
    suspend fun getAll(): List<ProductEntity>
    suspend fun findByBarcode(barcode: String): ProductEntity
    suspend fun findById(id: Int): ProductEntity
    suspend fun findByName(name: String): ProductEntity
}