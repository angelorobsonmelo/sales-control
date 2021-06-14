package com.angelorobson.product.data.model

data class ProductResponse(
    val id: Int,
    val name: String,
    val description: String,
    val price: Double,
    val barcode: String
)