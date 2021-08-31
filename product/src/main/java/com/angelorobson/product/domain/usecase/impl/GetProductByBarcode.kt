package com.angelorobson.product.domain.usecase.impl

import com.angelorobson.product.data.ProductRepository
import com.angelorobson.product.domain.mapper.ObjectDataToDomainMapper
import com.angelorobson.product.domain.model.ProductDomain
import com.angelorobson.product.domain.usecase.GetProductByBarcodeUseCase
import com.angelorobson.product.domain.usecase.GetProductByNameUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetProductByBarcode(
    private val repository: ProductRepository,
    private val mapperData: ObjectDataToDomainMapper
) : GetProductByBarcodeUseCase {

    override suspend fun invoke(barcode: String): Flow<List<ProductDomain>> {
        return flow {
            val items = repository.findByBarcode(barcode).map { mapperData.map(it) }
            emit(items)
        }.flowOn(Dispatchers.IO)
    }


}