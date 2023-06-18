package com.mcgregor.kaelapos.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mcgregor.kaelapos.dao.ProductDao
import com.mcgregor.kaelapos.models.Product

@Database(entities = [Product::class], version = 1, exportSchema = false)
abstract class KaelaPOSDatabase : RoomDatabase() {

    abstract val productDao: ProductDao

}