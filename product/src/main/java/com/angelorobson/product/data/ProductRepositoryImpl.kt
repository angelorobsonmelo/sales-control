package com.angelorobson.product.data

import com.angelorobson.product.data.datasource.local.LocalDataSource
import com.angelorobson.product.data.model.ProductData

class ProductRepositoryImpl(
    private val localDataSource: LocalDataSource
) : ProductRepository {

    override suspend fun insert(products: List<ProductData>) {

    }

    override suspend fun insert(product: ProductData): Long {
             return localDataSource.insert(product)
    }

    override suspend fun getAll(): List<ProductData> =
        localDataSource.getAll()

    override suspend fun findByBarcode(barcode: String): ProductData =
        localDataSource.findByBarcode(barcode)

    override suspend fun findById(id: Int): ProductData =
        localDataSource.findById(id)

    override suspend fun findByName(name: String): ProductData =
        localDataSource.findByName(name)


}