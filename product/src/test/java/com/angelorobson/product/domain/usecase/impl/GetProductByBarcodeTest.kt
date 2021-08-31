package com.angelorobson.product.domain.usecase.impl

import com.angelorobson.product.data.ProductRepository
import com.angelorobson.product.data.model.ProductData
import com.angelorobson.product.domain.mapper.ObjectDataToDomainMapper
import com.angelorobson.product.domain.usecase.GetProductByBarcodeUseCase
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class GetProductByBarcodeTest {

    private val repository: ProductRepository = mock()

    private val useCase: GetProductByBarcodeUseCase by lazy {
        GetProductByBarcode(repository, ObjectDataToDomainMapper())
    }

    @Test
    fun `When invoke barcode should return a product`() = runBlocking {
        // Given
        val barcode = "123"
        val products = (1..10).map { i ->
            ProductData(
                i.toLong(),
                "product $i",
                "description $i",
                10.0 + i,
                "12$i",
                isActive = true
            )
        }

        whenever(repository.findByBarcode(barcode)).thenReturn(products)

        // When
        val result = useCase.invoke(barcode)

        // Then
        result.collect {
            assertEquals(products.size, it.size)
        }
    }
}