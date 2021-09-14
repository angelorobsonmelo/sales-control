package com.angelorobson.product.presentation.fragments.products.products

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.angelorobson.product.R
import com.angelorobson.product.domain.model.ProductDomain
import com.angelorobson.product.domain.usecase.GetProductByBarcodeUseCase
import com.angelorobson.product.domain.usecase.GetProductByNameUseCase
import com.angelorobson.product.domain.usecase.GetProductsUseCase
import com.angelorobson.product.domain.usecase.InactiveProductUseCase
import com.angelorobson.product.presentation.mapper.ObjectDomainToPresentationMapper
import com.angelorobson.product.presentation.mapper.ObjectPresentationToDomainMapper
import com.angelorobson.product.presentation.viewmodel.ProductsViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module
import org.mockito.Mockito.mock


@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
@MediumTest
class ProductsFragmentTest {

    private val getProductsUseCase = mockk<GetProductsUseCase>(relaxed = true)
    private val getByNameUseCase = mockk<GetProductByNameUseCase>(relaxed = true)
    private val inactiveProductUseCase = mockk<InactiveProductUseCase>(relaxed = true)
    private val getByBarcode = mockk<GetProductByBarcodeUseCase>(relaxed = true)
    private val mapperDomain = ObjectDomainToPresentationMapper()
    private val mapperPresentationToDomainMapper = ObjectPresentationToDomainMapper()
    private val mockNavController = mockk<NavController>(relaxed = true)

    private lateinit var module: Module

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)

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
    fun tearDown() {
        unloadKoinModules(module)
    }

    @Test
    fun testNavigationToInGameScreen() {
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

        coEvery { getProductsUseCase.invoke() } returns flowOf(products)

        launchFragment()

        onView(withId(R.id.products_floating_action_button)).perform(click())
//        assertThat(mockNavController.currentDestination?.id).isEqualTo(R.id.)
    }


    private fun launchFragment() {
        mockNavController.setGraph(R.navigation.products_nav_graph)

        launchFragmentInContainer(
            themeResId = R.style.Theme_MaterialComponents_Light_NoActionBar
        ) {
            ProductsFragment().also { fragment ->
                fragment.viewLifecycleOwnerLiveData.observeForever { viewLifecycleOwner ->
                    if (viewLifecycleOwner != null) {
                        Navigation.setViewNavController(fragment.requireView(), mockNavController)
                    }
                }
            }
        }
    }


}