package com.angelorobson.product.data.datasource.local

import com.angelorobson.db.features.product.dao.ProductDao
import com.angelorobson.product.data.mapper.ObjectDataToEntityMapper
import com.angelorobson.product.data.mapper.ObjectEntityToDataMapper
import com.angelorobson.product.data.model.ProductData

class LocalDataSourceImpl(
    private val dao: ProductDao,
    private val mapperEntity: ObjectEntityToDataMapper,
    private val mapperToEntity: ObjectDataToEntityMapper
) : LocalDataSource {


    override suspend fun insert(products: List<ProductData>) {

    }

    override suspend fun insert(product: ProductData): Long = dao.insert(mapperToEntity.map(product))

    override suspend fun getAll(): List<ProductData> = dao.getAll().map { mapperEntity.map(it) }

    override suspend fun findByBarcode(barcode: String): ProductData =
        mapperEntity.map(dao.findByBarcode(barcode))

    override suspend fun findById(id: Int): ProductData = mapperEntity.map(dao.findById(id))

    override suspend fun findByName(name: String): ProductData = mapperEntity.map(dao.findByName(name))
}