package com.mcgregor.kaelapos.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "suppliers")
data class Supplier(

    @ColumnInfo(name = "supplier_name")
    val supplierName: String,
    @ColumnInfo(name = "supplier_phone")
    val supplierPhone: String,
    @ColumnInfo(name = "supplier_address")
    val supplierAddress: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
)
