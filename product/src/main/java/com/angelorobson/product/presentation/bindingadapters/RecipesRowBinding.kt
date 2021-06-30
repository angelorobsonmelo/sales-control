package com.angelorobson.product.presentation.bindingadapters

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.angelorobson.core.extensions.getCurrencyFormat

class ProductsRowBinding {

    companion object {

        @BindingAdapter("price")
        @JvmStatic
        fun currency(textView: TextView, price: Double) {
            textView.text = price.getCurrencyFormat()
        }


    }
}