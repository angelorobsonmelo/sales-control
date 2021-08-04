package com.angelorobson.product.domain.usecase

import com.angelorobson.product.domain.model.ProductSaveDomain
import kotlinx.coroutines.flow.Flow

interface EditProductUseCase {

    suspend operator fun invoke(productDomain: ProductSaveDomain): Flow<Nothing?>

}