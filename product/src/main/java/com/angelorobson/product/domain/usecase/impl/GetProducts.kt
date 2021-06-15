package com.angelorobson.product.domain.usecase.impl

import com.angelorobson.product.data.ProductRepository
import com.angelorobson.product.domain.mapper.ObjectDataToDomainMapper
import com.angelorobson.product.domain.model.ProductDomain
import com.angelorobson.product.domain.usecase.GetProductsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetProducts(
    private val repository: ProductRepository,
    private val mapperData: ObjectDataToDomainMapper
) : GetProductsUseCase {

    override suspend fun invoke(): Flow<List<ProductDomain>> {
        return flow {
            val items = repository.getAll().map { mapperData.map(it) }
            emit(items)
        }.flowOn(Dispatchers.IO)
    }


}