package com.angelorobson.core.extensions

import java.text.NumberFormat
import java.util.*


fun Double.getCurrencyFormat(format: String = "pt-BR"): String =
    NumberFormat.getCurrencyInstance(Locale.forLanguageTag(format)).format(this)

