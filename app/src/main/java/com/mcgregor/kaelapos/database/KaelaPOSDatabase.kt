package com.mcgregor.kaelapos.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.mcgregor.kaelapos.dao.ProductDao
import com.mcgregor.kaelapos.dao.ProductTransactionDao
import com.mcgregor.kaelapos.dao.SupplierDao
import com.mcgregor.kaelapos.models.Converters
import com.mcgregor.kaelapos.models.Product
import com.mcgregor.kaelapos.models.ProductTransaction
import com.mcgregor.kaelapos.models.Supplier

@TypeConverters(Converters::class)
@Database(entities = [Product::class, Supplier::class, ProductTransaction::class], version = 3, exportSchema = false)
abstract class KaelaPOSDatabase : RoomDatabase() {

    abstract val productDao: ProductDao
    abstract  val supplierDao: SupplierDao
    abstract val productTransactionDao: ProductTransactionDao
}