package com.angelorobson.product.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asLiveData
import com.angelorobson.core.utils.CallbackResult
import com.angelorobson.product.data.model.ProductData
import com.angelorobson.product.domain.model.ProductDomain
import com.angelorobson.product.domain.usecase.GetProductByBarcodeUseCase
import com.angelorobson.product.domain.usecase.GetProductByNameUseCase
import com.angelorobson.product.domain.usecase.GetProductsUseCase
import com.angelorobson.product.domain.usecase.InactiveProductUseCase
import com.angelorobson.product.presentation.mapper.ObjectDomainToPresentationMapper
import com.angelorobson.product.presentation.mapper.ObjectPresentationToDomainMapper
import com.angelorobson.product.presentation.model.ProductPresentation
import com.angelorobson.product.utils.MainCoroutineRule
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.lang.Exception

@ExperimentalCoroutinesApi
class ProductsViewModelTest {

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val getProductsUseCase: GetProductsUseCase = mock()
    private val getByNameUseCase: GetProductByNameUseCase = mock()
    private val inactiveProductUseCase: InactiveProductUseCase = mock()
    private val getByBarcode: GetProductByBarcodeUseCase = mock()
    private val mapperDomain = ObjectDomainToPresentationMapper()
    private val mapperPresentationToDomainMapper = ObjectPresentationToDomainMapper()

    private val viewModel: ProductsViewModel by lazy {
        ProductsViewModel(
            getProductsUseCase,
            getByNameUseCase,
            inactiveProductUseCase,
            getByBarcode,
            mapperDomain,
            mapperPresentationToDomainMapper
        )
    }

    @Test
    fun `When getProducts should a product list`() = runBlockingTest {
        // Given
        val products = (1..10).map { i ->
            ProductDomain(
                i.toLong(),
                "product $i",
                "description $i",
                10.0 + i,
                "12$i",
                isActive = true
            )
        }

        whenever(getProductsUseCase.invoke()).thenAnswer { flowOf(products) }

        // When
        viewModel.getProducts()

        // Then
        assertEquals(
            products.size,
            (viewModel.productsFlow.value as CallbackResult.Success).data?.size
        )
    }

    @Test
    fun `When getProducts should return an error`() = runBlockingTest {
        // Given
        whenever(getProductsUseCase.invoke()) doReturn callbackFlow { Exception("") }

        // When
        viewModel.getProducts()

        //  Then
        assertTrue(viewModel.productsFlow.value is CallbackResult.Error)
    }

    @Test
    fun `When findByName should return a product`() = runBlockingTest {
        // Given
        val name = "name"
        val products = (1..10).map { i ->
            ProductDomain(
                i.toLong(),
                "product $i",
                "description $i",
                10.0 + i,
                "12$i",
                isActive = true
            )
        }

        whenever(getByNameUseCase.invoke(eq(name))).thenAnswer { flowOf(products) }

        // When
        viewModel.findByName(name)

        // Then
        assertTrue((viewModel.productsFlow.value as CallbackResult.Success).data?.isNotEmpty()!!)
    }

    @Test
    fun `When findByName should return an error`() = runBlockingTest {
        // Given
        val name = "name"
        whenever(getByNameUseCase.invoke(eq(name))) doReturn callbackFlow { Exception("") }

        // When
        viewModel.findByName(name)

        //  Then
        assertTrue(viewModel.productsFlow.value is CallbackResult.Error)
    }

    @Test
    fun `When inactiveProduct should return success`() = runBlockingTest {
        // Given
        val productPresentation = ProductPresentation(
            name = "name",
            description = "description",
            price = 10.0,
            barcode = "123",
            isActive = true
        )
        whenever(inactiveProductUseCase.invoke(any())).thenAnswer { flowOf(null) }

        // When
        viewModel.inactiveProduct(productPresentation)

        // Then
        assertTrue(viewModel.inactiveProductFlow.value is CallbackResult.Success)
    }

    @Test
    fun `When inactiveProduct should return an error`() = runBlockingTest {
        // Given
        val productPresentation = ProductPresentation(
            name = "name",
            description = "description",
            price = 10.0,
            barcode = "123",
            isActive = true
        )
        whenever(inactiveProductUseCase.invoke(any())) doReturn callbackFlow { Exception("") }

        // When
        viewModel.inactiveProduct(productPresentation)

        // Then
        assertTrue(viewModel.inactiveProductFlow.value is CallbackResult.Error)
    }

    @Test
    fun `When findByBarcode should return a list of products`() = runBlockingTest {
        // Given
        val barcode = "123"
        val products = (1..10).map { i ->
            ProductDomain(
                i.toLong(),
                "product $i",
                "description $i",
                10.0 + i,
                "12$i",
                isActive = true
            )
        }

        whenever(getByBarcode.invoke(eq(barcode))).thenAnswer { flowOf(products) }

        // When
        viewModel.findByBarcode(barcode)

        // Then
        assertTrue(viewModel.productsFlow.value is CallbackResult.Success)
    }

    @Test
    fun `When findByBarcode should return an error`() = runBlockingTest {
        // Given
        val barcode = "123"
        whenever(getByBarcode.invoke(eq(barcode))) doReturn callbackFlow { Exception("") }

        // When
        viewModel.findByBarcode(barcode)

        // Then
        assertTrue(viewModel.productsFlow.value is CallbackResult.Error)
    }

}