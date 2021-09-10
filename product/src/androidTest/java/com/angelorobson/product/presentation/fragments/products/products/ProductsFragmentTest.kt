package com.angelorobson.product.presentation.fragments.products.products

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
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

@RunWith(AndroidJUnit4::class)
class ProductsFragmentTest {

    private val getProductsUseCase = mockk<GetProductsUseCase>(relaxed = true)
    private val getByNameUseCase = mockk<GetProductByNameUseCase>(relaxed = true)
    private val inactiveProductUseCase = mockk<InactiveProductUseCase>(relaxed = true)
    private val getByBarcode = mockk<GetProductByBarcodeUseCase>(relaxed = true)
    private val mapperDomain = ObjectDomainToPresentationMapper()
    private val mapperPresentationToDomainMapper = ObjectPresentationToDomainMapper()

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

        // Create a TestNavHostController
        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext()
        )

        // Create a graphical FragmentScenario for the TitleScreen
        val titleScenario = launchFragmentInContainer<ProductsFragment>(
            themeResId = R.style.Theme_MaterialComponents_Light_NoActionBar
        )

        titleScenario.onFragment { fragment ->
            // Set the graph on the TestNavHostController
            navController.setGraph(R.navigation.products_nav_graph)

            // Make the NavController available via the findNavController() APIs
            Navigation.setViewNavController(fragment.requireView(), navController)
        }

        // Verify that performing a click changes the NavController’s state
        onView(ViewMatchers.withId(R.id.products_floating_action_button)).perform(ViewActions.click())
//        assertThat(navController.currentDestination?.id).isEqualTo(R.id.in_game)
    }

}