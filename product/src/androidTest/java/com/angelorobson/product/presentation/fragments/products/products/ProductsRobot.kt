package com.angelorobson.product.presentation.fragments.products.products

//import androidx.test.espresso.contrib.RecyclerViewActions

import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.angelorobson.product.BaseRobot
import com.angelorobson.product.R
import com.angelorobson.product.presentation.adapters.ProductsAdapter
import com.angelorobson.product.withRecyclerView
import org.hamcrest.CoreMatchers


fun productsRobot(func: ProductsRobot.() -> Unit) = ProductsRobot().apply(func)

class ProductsRobot : BaseRobot() {

    fun productsRecyclerViewVisible() {
        isVisible(R.id.products_recycler_view)
    }

    fun visibleProductNameTextView() {
        isRecyclerViewChildIsVisible(
            0,
            R.id.products_recycler_view,
            R.id.product_item_name_text_view
        )
    }

    fun visibleProductDescriptionTextView() {
        isRecyclerViewChildIsVisible(
            0,
            R.id.products_recycler_view,
            R.id.product_item_description_text_view
        )
    }

    fun visibleProductPriceTextView() {
        isRecyclerViewChildIsVisible(
            0,
            R.id.products_recycler_view,
            R.id.product_item_price_text_view
        )
    }

    fun visibleNoDataFoundTextView() {
        isVisible(R.id.products_no_data_found_text_view)
    }

    fun swipeLeft(
    ) {
        swipeLeft(R.id.products_recycler_view)
    }

    /* fun clickOnRecyclerViewItem() {
         onView(withId(R.id.rv_conference_list)).perform(
             actionOnItemAtPosition(0, MyViewAction.clickChildViewWithId(R.id. bt_deliver))
     }*/


//    fun swipeRightItemInRecyclerView() {
//        onView(withId(R.id.products_recycler_view)).perform(
//            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
//                0, GeneralSwipeAction(
//                    Swipe.SLOW, GeneralLocation.BOTTOM_RIGHT, GeneralLocation.BOTTOM_LEFT,
//                    Press.FINGER
//                )
//            )
//        )
//    }

//    fun notVisibleButtonTryAgain() {
//        isNotVisible(R.id.ops_error_try_again_button)
//    }
//
//    fun visibleButtonTryAgain() {
//        isVisible(R.id.ops_error_try_again_button)
//    }


}