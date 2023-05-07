package com.mcgregor.kaelapos.repository

import com.mcgregor.kaelapos.models.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    fun getAllProducts(): Flow<List<Product>>

    suspend fun insertProduct(product: Product)

    suspend fun updateProduct(product: Product)

    suspend fun deleteProduct(product: Product)

    suspend fun getProductById(id: Int): Product?

}