package com.angelorobson.product.domain.usecase.impl

import com.angelorobson.product.data.ProductRepository
import com.angelorobson.product.domain.mapper.ObjectDataToDomainMapper
import com.angelorobson.product.domain.model.ProductDomain
import com.angelorobson.product.domain.usecase.GetProductByNameUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetProductByName(
    private val repository: ProductRepository,
    private val mapperData: ObjectDataToDomainMapper
) : GetProductByNameUseCase {

    override suspend fun invoke(name: String): Flow<List<ProductDomain>> {
        return flow {
            val term = "%$name%"
            val items = repository.findByTerm(term).map { mapperData.map(it) }
            emit(items)
        }.flowOn(Dispatchers.IO)
    }


}