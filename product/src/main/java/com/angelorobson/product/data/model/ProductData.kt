package com.angelorobson.product.data.model

data class ProductData(
    val id: Long = 0,
    val name: String,
    val description: String,
    val price: Double,
    val barcode: String
)