package com.angelorobson.product.data.datasource.local

import com.angelorobson.db.features.product.dao.ProductDao
import com.angelorobson.product.data.mapper.ObjectToDataMapper
import com.angelorobson.product.data.model.ProductData

class LocalDataSourceImpl(private val dao: ProductDao, private val mapper: ObjectToDataMapper) :
    LocalDataSource {


    override suspend fun insert(products: List<ProductData>) {

    }

    override suspend fun insert(product: ProductData) {

    }

    override suspend fun getAll(): List<ProductData> = dao.getAll().map { mapper.map(it) }

    override suspend fun findByBarcode(barcode: String): ProductData =
        mapper.map(dao.findByBarcode(barcode))

    override suspend fun findById(id: Int): ProductData = mapper.map(dao.findById(id))

    override suspend fun findByName(name: String): ProductData = mapper.map(dao.findByName(name))
}