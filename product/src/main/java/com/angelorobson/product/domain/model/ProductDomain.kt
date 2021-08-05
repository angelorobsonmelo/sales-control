package com.angelorobson.product.domain.model

data class ProductDomain(
    val id: Long = 0,
    val name: String,
    val description: String,
    val price: Double,
    val barcode: String,
    var isActive: Boolean
)