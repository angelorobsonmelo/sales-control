package com.angelorobson.product.presentation.fragments.products.products

import androidx.test.espresso.matcher.ViewMatchers.*
import com.angelorobson.product.BaseRobot
import com.angelorobson.product.R
import org.hamcrest.CoreMatchers.*

fun productsRobot(func: ProductsRobot.() -> Unit) = ProductsRobot().apply(func)

class ProductsRobot : BaseRobot() {

    fun productsRecyclerViewVisible() {
        isVisible(R.id.products_recycler_view)
    }

    fun visibleCountTextView(position: Int = 0, recyclerViewId: Int, recyclerViewChildId: Int) {
        isRecyclerViewChildIsVisible(position, recyclerViewId, recyclerViewChildId)
    }

//    fun notVisibleButtonTryAgain() {
//        isNotVisible(R.id.ops_error_try_again_button)
//    }
//
//    fun visibleButtonTryAgain() {
//        isVisible(R.id.ops_error_try_again_button)
//    }


}