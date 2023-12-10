package com.mcgregor.kaelapos.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.mcgregor.kaelapos.models.Product
import com.mcgregor.kaelapos.models.Supplier
import kotlinx.coroutines.flow.Flow

interface SupplierDao {

    @Query("SELECT * FROM suppliers")
    fun getSupplierList(): Flow<List<Supplier>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSupplier(supplier: Supplier)
    @Update
    suspend fun updateSupplier(supplier: Supplier)
    @Delete
    suspend fun deleteSupplier(supplier: Supplier)
    @Query("SELECT * FROM suppliers WHERE id = :id")
    suspend fun getSupplierById(id: Int): Supplier?
}