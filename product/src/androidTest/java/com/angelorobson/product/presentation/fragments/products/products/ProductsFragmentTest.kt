package com.angelorobson.product.presentation.fragments.products.products

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.angelorobson.product.R
import com.angelorobson.product.SaleControlBaseTest
import com.angelorobson.product.domain.model.ProductDomain
import com.nhaarman.mockitokotlin2.verify
import io.mockk.coEvery
import kotlinx.coroutines.flow.flowOf
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class ProductsFragmentTest: SaleControlBaseTest() {


    @Test
    fun when_click_on_add_product_should_navigate_to_addProductFragment() {
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

        productsRobot {
            click(R.id.products_floating_action_button)
        }

        verify(mockNavController).navigate(ProductsFragmentDirections.actionProductsFragmentToAddProductFragment())
    }

    @Test
    fun when_launch_screen_all_recycler_view_items_should_be_showed() {
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

        productsRobot {
            productsRecyclerViewVisible()
            visibleProductNameTextView()
            visibleProductDescriptionTextView()
            visibleProductPriceTextView()
        }
    }

    @Test
    fun when_launch_screen_with_list_empty_a_text_view_empty_data_should_show() {
        coEvery { getProductsUseCase.invoke() } returns flowOf(listOf())

        launchFragment()

        productsRobot {
            visibleNoDataFoundTextView()
        }
    }

    @Test
    fun when_launch_screen_and_swipe_item_to_left_should_show_edit_and_delete_options() {
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

        productsRobot {
            swipeLeft()
            print("")
        }
    }

    private fun launchFragment() {
        launchFragmentInContainer(themeResId = R.style.Theme_MaterialComponents_Light_NoActionBar) {

            ProductsFragment().also { fragment ->
                fragment.viewLifecycleOwnerLiveData.observeForever { viewLifecycleOwner ->
                    if (viewLifecycleOwner != null) {
                        Navigation.setViewNavController(
                            fragment.requireView(),
                            mockNavController
                        )
                    }
                }
            }
        }
    }


}