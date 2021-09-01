package com.angelorobson.product.presentation.viewmodel

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.angelorobson.core.utils.CallbackResult
import com.angelorobson.product.domain.usecase.InsertProductUseCase
import com.angelorobson.product.presentation.mapper.ObjectSaveProductPresentationToDomainMapper
import com.angelorobson.product.presentation.model.ProductToSavePresentation
import com.angelorobson.product.utils.MainCoroutineRule
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Rule

import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Exception
import kotlin.jvm.Throws

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class AddProductViewModelTest {

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val useCase: InsertProductUseCase = mock()
    private val mapper = ObjectSaveProductPresentationToDomainMapper()

    private val application = ApplicationProvider.getApplicationContext<Application>()

    private val viewModel: AddProductViewModel by lazy {
        AddProductViewModel(useCase, mapper, application)
    }

    @Test
    fun `When saveProduct should return success`() = runBlockingTest {
        // Given
        val id: Long = 1
        val productPresentation = ProductToSavePresentation(
            name = "name",
            description = "description",
            price = 10.0,
            barcode = "123"
        )

        whenever(useCase.invoke(any())).thenAnswer { flowOf(id) }

        // When
        viewModel.saveProduct(productPresentation)

        // Then
        assertEquals(id, (viewModel.saveResultFlow.value as CallbackResult.Success).data)
    }

    @Test
    fun `When saveProduct should return Error`() = runBlockingTest {
        // Given
        val errorMessage = "error"
        val productPresentation = ProductToSavePresentation(
            name = "name",
            description = "description",
            price = 10.0,
            barcode = "123"
        )

        whenever(useCase.invoke(any())) doReturn callbackFlow { Exception(errorMessage) }

        // When
        viewModel.saveProduct(productPresentation)


        // Then
        assertTrue(viewModel.saveResultFlow.value is CallbackResult.Error)
    }
}