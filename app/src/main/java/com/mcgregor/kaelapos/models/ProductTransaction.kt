package com.mcgregor.kaelapos.models

data class ProductTransaction(
    val product: Product,
    val productQuantity: Double,
    val productTotalAmount: Double
)
