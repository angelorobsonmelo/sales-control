package com.angelorobson.product.domain.usecase.impl

import com.angelorobson.product.data.ProductRepository
import com.angelorobson.product.data.model.ProductData
import com.angelorobson.product.domain.mapper.ObjectDataToDomainMapper
import com.angelorobson.product.domain.usecase.GetProductByNameUseCase
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class GetProductByNameTest {

    private val repository: ProductRepository = mock()
    private val useCase: GetProductByNameUseCase by lazy {
        GetProductByName(repository, ObjectDataToDomainMapper())
    }

    @Test
    fun `When getProductByName should return a product`() = runBlocking {
        // Given
        val name = "name"
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
        whenever(repository.findByTerm(any())).thenReturn(products)

        // When
        val result = useCase.invoke("123")

        // Then
        result.collect {
            assertEquals(products.size, it.size)
        }
    }

}