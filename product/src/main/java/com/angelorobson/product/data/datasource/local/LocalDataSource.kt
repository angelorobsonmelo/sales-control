package com.angelorobson.product.data.datasource.local

import com.angelorobson.product.domain.model.ProductDomain


interface LocalDataSource {

    suspend fun insert(products: List<ProductDomain>)
    suspend fun insert(product: ProductDomain)
    suspend fun getAll(): List<ProductDomain>
    suspend fun findByBarcode(barcode: String): ProductDomain
    suspend fun findById(id: Int): ProductDomain
    suspend fun findByName(name: String): ProductDomain
}