package com.mcgregor.kaelapos.repository

import com.mcgregor.kaelapos.models.Product
import com.mcgregor.kaelapos.models.ProductTransaction
import com.mcgregor.kaelapos.models.Supplier
import kotlinx.coroutines.flow.Flow

interface KaelaPOSRepository {

    fun getAllProducts(): Flow<List<Product>>

    suspend fun insertProduct(product: Product)

    suspend fun updateProduct(product: Product)

    suspend fun deleteProduct(product: Product)

    suspend fun getProductById(id: Int): Product?

    fun getAllSuppliers(): Flow<List<Supplier>>

    suspend fun insertSupplier(supplier: Supplier)

    suspend fun updateSupplier(supplier: Supplier)

    suspend fun deleteSupplier(supplier: Supplier)

    suspend fun getSupplierById(id: Int): Supplier?

    fun getAllProductTransactions(): Flow<List<ProductTransaction>>

    suspend fun insertProductTransaction(productTransaction: ProductTransaction)

    suspend fun updateProductTransaction(productTransaction: ProductTransaction)

    suspend fun deleteProductTransaction(productTransaction: ProductTransaction)

    suspend fun getProductTransactionById(id: Int): ProductTransaction?

}