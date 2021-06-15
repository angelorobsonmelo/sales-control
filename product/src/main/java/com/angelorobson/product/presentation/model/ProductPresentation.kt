package com.angelorobson.product.presentation.model

data class ProductPresentation(
    val id: Int = 0,
    val name: String,
    val description: String,
    val price: Double,
    val barcode: String
)