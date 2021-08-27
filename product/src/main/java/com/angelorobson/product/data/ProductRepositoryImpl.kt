package com.angelorobson.product.data

import com.angelorobson.product.data.datasource.local.ProductLocalDataSource
import com.angelorobson.product.data.model.ProductData
import com.angelorobson.product.data.model.ProductSaveData

class ProductRepositoryImpl(
    private val productLocalDataSource: ProductLocalDataSource
) : ProductRepository {

    override suspend fun insert(products: List<ProductData>) {

    }

    override suspend fun insert(product: ProductSaveData): Long {
        return productLocalDataSource.insert(product)
    }

    override suspend fun getAll(): List<ProductData> =
        productLocalDataSource.getAll()

    override suspend fun findByBarcode(barcode: String): List<ProductData> =
        productLocalDataSource.findByBarcode(barcode)

    override suspend fun findById(id: Long): ProductData =
        productLocalDataSource.findById(id)

    override suspend fun findByName(name: String): ProductData =
        productLocalDataSource.findByName(name)

    override suspend fun findByTerm(name: String): List<ProductData> {
        return productLocalDataSource.findByTerm(name)
    }

    override suspend fun update(product: ProductSaveData) {
        return productLocalDataSource.update(product)
    }

    override suspend fun inactivateProduct(product: ProductData) {
        productLocalDataSource.inactivateProduct(product)
    }


}