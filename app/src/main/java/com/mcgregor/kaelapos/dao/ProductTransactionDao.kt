package com.mcgregor.kaelapos.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.mcgregor.kaelapos.models.ProductTransaction
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductTransactionDao {
    @Query("SELECT * FROM product_transactions")
    fun getProductTransactionsList(): Flow<List<ProductTransaction>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertProductTransaction(productTransaction: ProductTransaction)

    @Update
    suspend fun updateProductTransaction(productTransaction: ProductTransaction)

    @Delete
    suspend fun deleteProductTransaction(productTransaction: ProductTransaction)

    @Query("SELECT * FROM product_transactions WHERE id = :id")
    suspend fun getProductTransactionById(id: Int): ProductTransaction?
}