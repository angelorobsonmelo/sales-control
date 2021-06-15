package com.angelorobson.product.data

import com.angelorobson.product.data.datasource.local.LocalDataSource
import com.angelorobson.product.domain.mapper.ObjectToPresentationMapper
import com.angelorobson.product.presentation.model.ProductPresentation

class ProductRepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val dataMapper: ObjectToPresentationMapper
) : ProductRepository {

    override suspend fun insert(products: List<ProductPresentation>) {

    }

    override suspend fun insert(product: ProductPresentation) {

    }

    override suspend fun getAll(): List<ProductPresentation> =
        localDataSource.getAll().map { dataMapper.map(it) }

    override suspend fun findByBarcode(barcode: String): ProductPresentation =
        dataMapper.map(localDataSource.findByBarcode(barcode))

    override suspend fun findById(id: Int): ProductPresentation =
        dataMapper.map(localDataSource.findById(id))

    override suspend fun findByName(name: String): ProductPresentation =
        dataMapper.map(localDataSource.findByName(name))


}