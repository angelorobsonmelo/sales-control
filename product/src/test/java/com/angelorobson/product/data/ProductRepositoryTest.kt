package com.angelorobson.product.data

import com.angelorobson.product.data.datasource.local.ProductLocalDataSource
import com.angelorobson.product.data.model.ProductData
import com.angelorobson.product.data.model.ProductSaveData
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test


class ProductRepositoryTest {


    private val localDataSourceMock: ProductLocalDataSource = mock()

    private val repository: ProductRepository by lazy {
        ProductRepositoryImpl(localDataSourceMock)
    }

    @Test
    fun `When insert from repository should return id bigger then 0`() = runBlocking {
        // Given
        val product = ProductSaveData(1L, "", "", 0.0, "")
        whenever(localDataSourceMock.insert(eq(product))).thenReturn(1L)

        // When
        val id = repository.insert(product)

        // Then
        assertEquals(1L, id)
    }

    @Test
    fun `When getAll from repository should return products list`() = runBlocking {
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

        whenever(localDataSourceMock.getAll()).thenReturn(products)

        // When
        val productsReturned = repository.getAll()

        // Then
        assertEquals(products.size, productsReturned.size)
    }

    @Test
    fun `When findByBarcode should return a product`() = runBlocking {
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
        whenever(localDataSourceMock.findByBarcode(eq(barcode))).thenReturn(products)

        // When
        val productsReturned = repository.findByBarcode(barcode)

        // Then
        assertEquals(products.size, productsReturned.size)
    }

    @Test
    fun `When findById should return a product`() = runBlocking {
        // Given
        val id: Long = 1
        val productData = ProductData(
            id,
            "product",
            "description",
            10.0,
            "12",
            isActive = true
        )
        whenever(localDataSourceMock.findById(eq(id))).thenReturn(productData)

        // When
        val productActual = repository.findById(id)

        // Then
        assertEquals(id, productActual.id)
    }

    @Test
    fun `When findByName should return a product`() = runBlocking {
        // Given
        val name = "product name"
        val productData = ProductData(
            1L,
            name,
            "description",
            10.0,
            "12",
            isActive = true
        )
        whenever(localDataSourceMock.findByName(eq(name))).thenReturn(productData)

        // When
        val product = repository.findByName(name)

        // Then
        assertEquals(name, product.name)
    }

    @Test
    fun `When findByTerm should return a list of products`() = runBlocking {
        // Given
        val term = "term"
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
        whenever(localDataSourceMock.findByTerm(eq(term))).thenReturn(products)

        // When
        val listProductActual = repository.findByTerm(term)

        // Then
        assertEquals(products.size, listProductActual.size)
    }

}