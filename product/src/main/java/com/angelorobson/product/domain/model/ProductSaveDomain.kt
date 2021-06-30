package com.angelorobson.product.domain.model

data class ProductSaveDomain(
    val id: Long = 0,
    val name: String,
    val description: String,
    val price: Double,
    val barcode: String
)