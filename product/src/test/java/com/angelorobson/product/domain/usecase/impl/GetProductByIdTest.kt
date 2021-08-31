package com.angelorobson.product.domain.usecase.impl

import com.angelorobson.product.data.ProductRepository
import com.angelorobson.product.data.model.ProductData
import com.angelorobson.product.domain.mapper.ObjectDataToDomainMapper
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class GetProductByIdTest {

    private val repository: ProductRepository = mock()
    private val useCase by lazy {
        GetProductById(repository, ObjectDataToDomainMapper())
    }

    @Test
    fun `When getProductById should return a product`() = runBlocking {
        // Given
        val id = 1L
        val productData = ProductData(
            id,
            "product",
            "description",
            10.0,
            "12",
            isActive = true
        )
        whenever(repository.findById(eq(id))).thenReturn(productData)

        // When
        val result = useCase.invoke(id)

        // Then
        result.collect {
            Assert.assertEquals(id, it.id)
        }
    }
}