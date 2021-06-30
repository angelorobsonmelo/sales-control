package com.angelorobson.product.presentation.model

data class ProductToSavePresentation(
    val id: Long = 0,
    var name: String = "",
    var description: String = "",
    var price: Double = 0.0,
    var barcode: String = ""
)