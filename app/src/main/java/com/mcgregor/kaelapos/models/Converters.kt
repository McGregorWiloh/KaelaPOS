package com.mcgregor.kaelapos.models
import androidx.room.TypeConverter
import com.google.gson.Gson

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromProduct(product: Product): String {
        return gson.toJson(product)
    }

    @TypeConverter
    fun toProduct(productString: String): Product {
        return Gson().fromJson(productString, Product::class.java)
    }

    @TypeConverter
    fun fromSupplier(supplier: Supplier): String {
        return Gson().toJson(supplier)
    }

    @TypeConverter
    fun toSupplier(supplierString: String): Supplier {
        return Gson().fromJson(supplierString, Supplier::class.java)
    }
}