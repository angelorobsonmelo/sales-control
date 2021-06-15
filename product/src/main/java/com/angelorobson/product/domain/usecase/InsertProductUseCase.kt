package com.angelorobson.product.domain.usecase

import com.angelorobson.product.domain.model.ProductDomain
import kotlinx.coroutines.flow.Flow

interface InsertProductUseCase {

    suspend operator fun invoke(productDomain: ProductDomain): Flow<Long>

}