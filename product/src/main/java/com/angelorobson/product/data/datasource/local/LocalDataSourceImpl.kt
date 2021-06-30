package com.angelorobson.product.data.datasource.local

import com.angelorobson.db.features.product.dao.ProductDao
import com.angelorobson.product.data.mapper.ObjectDataToEntityMapper
import com.angelorobson.product.data.mapper.ObjectEntityToDataMapper
import com.angelorobson.product.data.mapper.ObjectProductSaveDataToEntityMapper
import com.angelorobson.product.data.model.ProductData
import com.angelorobson.product.data.model.ProductSaveData

class LocalDataSourceImpl(
    private val dao: ProductDao,
    private val mapperEntity: ObjectEntityToDataMapper,
    private val mapperSaveDomainToEntity: ObjectProductSaveDataToEntityMapper,
) : LocalDataSource {


    override suspend fun insert(products: List<ProductData>) {

    }

    override suspend fun insert(product: ProductSaveData): Long =
        dao.insert(mapperSaveDomainToEntity.map(product))

    override suspend fun getAll(): List<ProductData> = dao.getAll().map { mapperEntity.map(it) }

    override suspend fun findByBarcode(barcode: String): ProductData =
        mapperEntity.map(dao.findByBarcode(barcode))

    override suspend fun findById(id: Int): ProductData = mapperEntity.map(dao.findById(id))

    override suspend fun findByName(name: String): ProductData =
        mapperEntity.map(dao.findByName(name))

    override suspend fun findByTerm(name: String): List<ProductData> {
        return dao.findByTerm(name).map { mapperEntity.map(it) }
    }
}