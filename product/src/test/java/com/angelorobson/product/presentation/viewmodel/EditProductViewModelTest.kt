package com.angelorobson.product.presentation.viewmodel

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.angelorobson.core.utils.CallbackResult
import com.angelorobson.product.domain.model.ProductDomain
import com.angelorobson.product.domain.usecase.EditProductUseCase
import com.angelorobson.product.domain.usecase.GetProductByIdUseCase
import com.angelorobson.product.presentation.mapper.ObjectDomainToSaveProductPresentationMapper
import com.angelorobson.product.presentation.mapper.ObjectSaveProductPresentationToDomainMapper
import com.angelorobson.product.presentation.model.ProductToSavePresentation
import com.angelorobson.product.utils.MainCoroutineRule
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Rule

import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class EditProductViewModelTest {

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val getProductByIdUseCase: GetProductByIdUseCase = mock()
    private val mapper = ObjectSaveProductPresentationToDomainMapper()
    private val domainToSaveProductPresentationMapper =
        ObjectDomainToSaveProductPresentationMapper()
    private val editProductUseCase: EditProductUseCase = mock()
    private val application = ApplicationProvider.getApplicationContext<Application>()

    private val viewModel: EditProductViewModel by lazy {
        EditProductViewModel(
            getProductByIdUseCase,
            mapper,
            domainToSaveProductPresentationMapper,
            editProductUseCase,
            application
        )
    }

    @Test
    fun `When saveProduct should return success`() = runBlockingTest {
        // Given
        val product =
            ProductToSavePresentation(name = "123", barcode = "", price = 10.0, description = "554")

        whenever(editProductUseCase.invoke(any())).thenAnswer { flowOf(null) }

        // When
        viewModel.saveProduct(product)

        // Then
        assertTrue(viewModel.saveResultFlow.value is CallbackResult.Success)
    }

    @Test
    fun `When getProduct should return a product`() = runBlockingTest {
        // Given
        val id = 1L
        val product = ProductDomain(
            1L,
            "product",
            "description ",
            10.0,
            "12",
            isActive = true
        )
        whenever(getProductByIdUseCase.invoke(eq(id))).thenAnswer { flowOf(product) }

        // When
        viewModel.getProduct(id)

        //  Then
        assertEquals(id, (viewModel.productFlow.value as CallbackResult.Success).data?.id)
    }
}