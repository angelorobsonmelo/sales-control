package com.angelorobson.product.presentation.fragments.products.products


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import com.angelorobson.product.BaseRobot
import com.angelorobson.product.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import androidx.test.espresso.action.Press

import androidx.test.espresso.action.GeneralLocation

import androidx.test.espresso.action.Swipe

import androidx.test.espresso.action.GeneralSwipeAction

import androidx.recyclerview.widget.RecyclerView

import androidx.test.espresso.action.CoordinatesProvider

import androidx.test.espresso.action.Tap

import androidx.test.espresso.action.GeneralClickAction

import androidx.test.espresso.ViewAction





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

    fun clickOnEditOption() {
//        onView(withId(R.id.products_recycler_view)).perform(
//            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
//                0, GeneralSwipeAction(
//                    Swipe.SLOW, GeneralLocation.BOTTOM_RIGHT, GeneralLocation.BOTTOM_LEFT,
//                    Press.FINGER
//                )
//            )
//        )

        onView(withId(R.id.products_recycler_view)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0, GeneralSwipeAction(
                    Swipe.SLOW, GeneralLocation.BOTTOM_RIGHT, GeneralLocation.BOTTOM_LEFT,
                    Press.FINGER
                )
            )
        ).perform(clickXY(50, 0))

//        onView(withId(R.id.products_recycler_view)).check(matches(withText("Edit")))

    }


}

fun clickXY(x: Int, y: Int): ViewAction? {
    return GeneralClickAction(
        Tap.SINGLE,
        { view ->
            val screenPos = IntArray(2)
            view.getLocationOnScreen(screenPos)
            val screenX = (screenPos[0] + x).toFloat()
            val screenY = (screenPos[1] + y).toFloat()
            floatArrayOf(screenX, screenY)
        },
        Press.FINGER
    )
}

private fun childAtPosition(
    parentMatcher: Matcher<View>, position: Int
): Matcher<View> {

    return object : TypeSafeMatcher<View>() {
        override fun describeTo(description: Description) {
            description.appendText("Child at position $position in parent ")
            parentMatcher.describeTo(description)
        }

        public override fun matchesSafely(view: View): Boolean {
            val parent = view.parent
            return parent is ViewGroup && parentMatcher.matches(parent)
                    && view == parent.getChildAt(position)
        }
    }
}