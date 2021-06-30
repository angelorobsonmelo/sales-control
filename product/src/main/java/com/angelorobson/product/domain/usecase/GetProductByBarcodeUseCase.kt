package com.angelorobson.product.domain.usecase

import com.angelorobson.product.domain.model.ProductDomain
import kotlinx.coroutines.flow.Flow

interface GetProductByBarcodeUseCase {

    suspend operator fun invoke(barcode: String): Flow<List<ProductDomain>>

}