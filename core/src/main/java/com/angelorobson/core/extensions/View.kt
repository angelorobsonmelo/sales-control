package com.angelorobson.core.extensions

import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.angelorobson.core.R
import com.google.android.material.snackbar.Snackbar

fun View.displaySnackBar(
    text: String,
    duration: Int = Snackbar.LENGTH_LONG,
    textColorSnackBar: Int = Color.WHITE,
    backgroundColorSnackBar: Int = ContextCompat.getColor(
        this.context,
        R.color.red_snack_bar_error
    )
) {
    val snackBar =
        Snackbar.make(
            this,
            text,
            duration
        ).setBackgroundTint(backgroundColorSnackBar)

    val sbView = snackBar.view
    val snackView =
        sbView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
    snackView.setTextColor(textColorSnackBar)
    snackBar.show()
}