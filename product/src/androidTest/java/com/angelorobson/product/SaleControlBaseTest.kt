package com.angelorobson.product

import androidx.navigation.NavController
import com.angelorobson.product.domain.usecase.GetProductByBarcodeUseCase
import com.angelorobson.product.domain.usecase.GetProductByNameUseCase
import com.angelorobson.product.domain.usecase.GetProductsUseCase
import com.angelorobson.product.domain.usecase.InactiveProductUseCase
import com.angelorobson.product.presentation.mapper.ObjectDomainToPresentationMapper
import com.angelorobson.product.presentation.mapper.ObjectPresentationToDomainMapper
import com.angelorobson.product.presentation.viewmodel.ProductsViewModel
import io.mockk.MockKAnnotations
import io.mockk.mockk
import org.junit.After
import org.junit.Before
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module
import org.mockito.Mockito
import org.mockito.MockitoAnnotations


open class SaleControlBaseTest {

    val getProductsUseCase = mockk<GetProductsUseCase>(relaxed = true)
    private val getByNameUseCase = mockk<GetProductByNameUseCase>(relaxed = true)
    private val inactiveProductUseCase = mockk<InactiveProductUseCase>(relaxed = true)
    private val getByBarcode = mockk<GetProductByBarcodeUseCase>(relaxed = true)
    private val mapperDomain = ObjectDomainToPresentationMapper()
    private val mapperPresentationToDomainMapper = ObjectPresentationToDomainMapper()
    val mockNavController = Mockito.mock(NavController::class.java)

    private lateinit var module: Module

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)
        MockitoAnnotations.initMocks(this)

        module = module(override = true) {
            viewModel {
                ProductsViewModel(
                    getProductsUseCase,
                    getByNameUseCase,
                    inactiveProductUseCase,
                    getByBarcode,
                    mapperDomain,
                    mapperPresentationToDomainMapper
                )
            }
        }

        loadKoinModules(module)
    }

    @After
    fun cleanUp() {
        unloadKoinModules(module)
    }

}