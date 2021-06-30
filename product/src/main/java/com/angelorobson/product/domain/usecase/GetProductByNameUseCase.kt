package com.angelorobson.product.domain.usecase

import com.angelorobson.product.domain.model.ProductDomain
import kotlinx.coroutines.flow.Flow

interface GetProductByNameUseCase {

    suspend operator fun invoke(name: String): Flow<List<ProductDomain>>

}