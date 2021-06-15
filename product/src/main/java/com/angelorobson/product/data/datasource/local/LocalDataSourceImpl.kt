package com.angelorobson.product.data.datasource.local

import com.angelorobson.db.features.product.dao.ProductDao
import com.angelorobson.product.data.mapper.ObjectToDomainMapper
import com.angelorobson.product.domain.model.ProductDomain

class LocalDataSourceImpl(private val dao: ProductDao, private val mapper: ObjectToDomainMapper) :
    LocalDataSource {


    override suspend fun insert(products: List<ProductDomain>) {

    }

    override suspend fun insert(product: ProductDomain) {

    }

    override suspend fun getAll(): List<ProductDomain> = dao.getAll().map { mapper.map(it) }

    override suspend fun findByBarcode(barcode: String): ProductDomain =
        mapper.map(dao.findByBarcode(barcode))

    override suspend fun findById(id: Int): ProductDomain = mapper.map(dao.findById(id))

    override suspend fun findByName(name: String): ProductDomain = mapper.map(dao.findByName(name))
}