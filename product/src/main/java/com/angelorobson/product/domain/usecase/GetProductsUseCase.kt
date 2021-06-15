package com.angelorobson.product.domain.usecase

import com.angelorobson.product.presentation.model.ProductPresentation
import kotlinx.coroutines.flow.Flow

interface GetProductsUseCase {

    suspend operator fun invoke(): Flow<List<ProductPresentation>>
}