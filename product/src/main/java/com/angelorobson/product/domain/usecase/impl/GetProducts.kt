package com.angelorobson.product.domain.usecase.impl

import com.angelorobson.product.data.ProductRepository
import com.angelorobson.product.domain.usecase.GetProductsUseCase
import com.angelorobson.product.presentation.model.ProductPresentation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetProducts(
    private val repository: ProductRepository
) : GetProductsUseCase {

    override suspend fun invoke(): Flow<List<ProductPresentation>> {
        return flow {
            emit(repository.getAll())
        }
    }


}