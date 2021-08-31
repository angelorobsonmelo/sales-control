package com.angelorobson.product.domain.usecase.impl

import com.angelorobson.product.data.ProductRepository
import com.angelorobson.product.data.model.ProductSaveData
import com.angelorobson.product.domain.mapper.ObjectProductSaveDomainToDataMapper
import com.angelorobson.product.domain.model.ProductSaveDomain
import com.angelorobson.product.domain.usecase.InsertProductUseCase
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.anyArray
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class InsertProductTest {

    private val repository: ProductRepository = mock()

    private val useCase: InsertProductUseCase by lazy {
        InsertProduct(repository, ObjectProductSaveDomainToDataMapper())
    }

    @Test
    fun `When insertProduct should return an id inserted`() = runBlocking {
        // Given
        val id: Long = 1
        val productSave = ProductSaveData(
            id = id,
            name = "name",
            barcode = "123",
            description = "description",
            price = 10.0
        )
        val productSaveDomain = ProductSaveDomain(
            name = "name",
            price = 10.0,
            description = "description",
            barcode = "barcode"
        )

        whenever(repository.insert(any<ProductSaveData>())).thenReturn(id)

        // When
        val result = useCase.invoke(productSaveDomain)

        // Then
        result.collect {
            Assert.assertEquals(id, it)
        }
    }
}