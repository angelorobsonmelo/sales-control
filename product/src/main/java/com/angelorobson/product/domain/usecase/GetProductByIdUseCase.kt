package com.angelorobson.product.domain.usecase

import com.angelorobson.product.domain.model.ProductDomain
import com.angelorobson.product.presentation.model.ProductPresentation
import kotlinx.coroutines.flow.Flow

interface GetProductByIdUseCase {

    suspend operator fun invoke(id: Long): Flow<ProductDomain>
}