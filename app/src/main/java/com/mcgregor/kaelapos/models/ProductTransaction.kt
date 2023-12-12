package com.mcgregor.kaelapos.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product_transactions")
data class ProductTransaction(
    @ColumnInfo(name = "product")
    val product: Product,
    @ColumnInfo(name = "product_quantity")
    val productQuantity: Double,
    @ColumnInfo(name = "product_total_amount")
    val productTotalAmount: Double,
    @ColumnInfo(name = "supplier")
    val supplier: Supplier = Supplier("","",""), // This is for when we are receiving inventory
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
