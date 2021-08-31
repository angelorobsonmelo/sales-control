package com.angelorobson.product.domain.usecase.impl

import com.angelorobson.product.data.ProductRepository
import com.angelorobson.product.data.model.ProductData
import com.angelorobson.product.domain.mapper.ObjectDataToDomainMapper
import com.angelorobson.product.domain.usecase.GetProductsUseCase
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class GetProductsUseCaseTest {

    private val repository: ProductRepository = mock()

    private val useCase: GetProductsUseCase by lazy {
        GetProducts(repository, ObjectDataToDomainMapper())
    }

    @Test
    fun `When invoke should return a product`() = runBlocking {
        // Given
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
        whenever(repository.getAll()).thenReturn(products)

        // When
        val result = useCase.invoke()

        // Then
        result.collect {
            Assert.assertEquals(products.size, it.size)
        }
    }
}