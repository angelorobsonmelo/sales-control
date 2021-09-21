package com.angelorobson.product.presentation.fragments.products.products

import com.angelorobson.product.BaseRobot
import com.angelorobson.product.R

fun productsRobot(func: ProductsRobot.() -> Unit) = ProductsRobot().apply(func)

class ProductsRobot : BaseRobot() {

    fun productsRecyclerViewVisible() {
        isVisible(R.id.products_recycler_view)
    }

    fun visibleProductNameTextView() {
        isRecyclerViewChildIsVisible(0, R.id.products_recycler_view, R.id.product_item_name_text_view)
    }

    fun visibleProductDescriptionTextView() {
        isRecyclerViewChildIsVisible(0, R.id.products_recycler_view, R.id.product_item_description_text_view)
    }

    fun visibleProductPriceTextView() {
        isRecyclerViewChildIsVisible(0, R.id.products_recycler_view, R.id.product_item_price_text_view)
    }

//    fun notVisibleButtonTryAgain() {
//        isNotVisible(R.id.ops_error_try_again_button)
//    }
//
//    fun visibleButtonTryAgain() {
//        isVisible(R.id.ops_error_try_again_button)
//    }


}