package com.angelorobson.product.domain.usecase.impl

import com.angelorobson.product.data.ProductRepository
import com.angelorobson.product.domain.mapper.ObjectProductSaveDomainToDataMapper
import com.angelorobson.product.domain.model.ProductSaveDomain
import com.angelorobson.product.domain.usecase.EditProductUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class EditProduct(
    private val repository: ProductRepository,
    private val mapper: ObjectProductSaveDomainToDataMapper
) : EditProductUseCase {


    override suspend fun invoke(productDomain: ProductSaveDomain): Flow<Nothing?> {
        return flow {
            repository.update(mapper.map(productDomain))
            emit(null)
        }.flowOn(Dispatchers.IO)
    }
}