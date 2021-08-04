package com.angelorobson.product.domain.usecase.impl

import com.angelorobson.product.data.ProductRepository
import com.angelorobson.product.domain.mapper.ObjectDataToDomainMapper
import com.angelorobson.product.domain.model.ProductDomain
import com.angelorobson.product.domain.usecase.GetProductByIdUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetProductById(
    private val repository: ProductRepository,
    private val mapperData: ObjectDataToDomainMapper
) : GetProductByIdUseCase {

    override suspend fun invoke(id: Long): Flow<ProductDomain> {
        return flow {
            val item = repository.findById(id)
            emit(mapperData.map(item))
        }
    }


}