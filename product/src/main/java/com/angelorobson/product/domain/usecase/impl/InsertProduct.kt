package com.angelorobson.product.domain.usecase.impl

import com.angelorobson.product.data.ProductRepository
import com.angelorobson.product.domain.mapper.ObjectDomainToDataMapper
import com.angelorobson.product.domain.model.ProductDomain
import com.angelorobson.product.domain.usecase.InsertProductUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class InsertProduct(
    private val repository: ProductRepository,
    private val mapper: ObjectDomainToDataMapper
) : InsertProductUseCase {


    override suspend fun invoke(productDomain: ProductDomain): Flow<Long> {
       return flow {
            val id = repository.insert(mapper.map(productDomain))
            emit(id)
        }.flowOn(Dispatchers.IO)
    }
}