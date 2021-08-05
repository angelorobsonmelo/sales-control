package com.angelorobson.product.domain.usecase.impl

import com.angelorobson.product.data.ProductRepository
import com.angelorobson.product.domain.mapper.ObjectDomainToDataMapper
import com.angelorobson.product.domain.model.ProductDomain
import com.angelorobson.product.domain.usecase.EditProductUseCase
import com.angelorobson.product.domain.usecase.InactiveProductUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class InactiveProduct(
    private val repository: ProductRepository,
    private val mapper: ObjectDomainToDataMapper
) : InactiveProductUseCase {


    override suspend fun invoke(productDomain: ProductDomain): Flow<Nothing?> {
        return flow {
            mapper.map(productDomain).run {
                isActive = false
                repository.inactivateProduct(this)
            }
            emit(null)
        }.flowOn(Dispatchers.IO)
    }
}