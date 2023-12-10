package com.mcgregor.kaelapos.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mcgregor.kaelapos.dao.ProductDao
import com.mcgregor.kaelapos.dao.SupplierDao
import com.mcgregor.kaelapos.models.Product
import com.mcgregor.kaelapos.models.Supplier

@Database(entities = [Product::class, Supplier::class], version = 2, exportSchema = false)
abstract class KaelaPOSDatabase : RoomDatabase() {

    abstract val productDao: ProductDao
    abstract  val supplierDao: SupplierDao

}