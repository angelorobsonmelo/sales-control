package com.angelorobson.product.data

import com.angelorobson.db.features.product.dao.ProductDao
import com.angelorobson.db.features.product.entities.ProductEntity

class ProductRepositoryImpl(productDao: ProductDao) : ProductRepository {

    override suspend fun insert(product: List<ProductEntity>) {
        TODO("Not yet implemented")
    }

    override suspend fun insert(product: ProductEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun getAll(): List<ProductEntity> {
        TODO("Not yet implemented")

    }

    override suspend fun findByBarcode(barcode: String): ProductEntity {
        TODO("Not yet implemented")
    }

    override suspend fun findById(id: Int): ProductEntity {
        TODO("Not yet implemented")
    }

    override suspend fun findByName(name: String): ProductEntity {
        TODO("Not yet implemented")
    }
}