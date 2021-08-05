package com.angelorobson.product.domain.usecase

import com.angelorobson.product.domain.model.ProductDomain
import kotlinx.coroutines.flow.Flow

interface InactiveProductUseCase {

    suspend operator fun invoke(productDomain: ProductDomain): Flow<Nothing?>

}