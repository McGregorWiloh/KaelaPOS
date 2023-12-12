package com.mcgregor.kaelapos.repository

import com.mcgregor.kaelapos.dao.ProductDao
import com.mcgregor.kaelapos.dao.ProductTransactionDao
import com.mcgregor.kaelapos.dao.SupplierDao
import com.mcgregor.kaelapos.models.Product
import com.mcgregor.kaelapos.models.ProductTransaction
import com.mcgregor.kaelapos.models.Supplier
import kotlinx.coroutines.flow.Flow

class KaelaPOSRepositoryImp(
    private val productDao: ProductDao,
    private val supplierDao: SupplierDao,
    private val productTransactionDao: ProductTransactionDao
): KaelaPOSRepository {

    override fun getAllProducts(): Flow<List<Product>> {
        return productDao.getProductList()
    }

    override suspend fun insertProduct(product: Product) {
        productDao.insertProduct(product)
    }

    override suspend fun updateProduct(product: Product) {
        productDao.updateProduct(product)
    }

    override suspend fun deleteProduct(product: Product) {
        productDao.deleteProduct(product)
    }

    override suspend fun getProductById(id: Int): Product? {
        return productDao.getProductById(id)
    }

    override fun getAllSuppliers(): Flow<List<Supplier>> {
        return supplierDao.getSupplierList()
    }

    override suspend fun insertSupplier(supplier: Supplier) {
        supplierDao.insertSupplier(supplier)
    }

    override suspend fun updateSupplier(supplier: Supplier) {
        supplierDao.updateSupplier(supplier)
    }

    override suspend fun deleteSupplier(supplier: Supplier) {
        supplierDao.deleteSupplier(supplier)
    }

    override suspend fun getSupplierById(id: Int): Supplier? {
        return supplierDao.getSupplierById(id)
    }

    override fun getAllProductTransactions(): Flow<List<ProductTransaction>> {
        return productTransactionDao.getProductTransactionsList()
    }

    override suspend fun insertProductTransaction(productTransaction: ProductTransaction) {
        productTransactionDao.insertProductTransaction(productTransaction)
    }

    override suspend fun updateProductTransaction(productTransaction: ProductTransaction) {
        productTransactionDao.updateProductTransaction(productTransaction)
    }

    override suspend fun deleteProductTransaction(productTransaction: ProductTransaction) {
        productTransactionDao.deleteProductTransaction(productTransaction)
    }

    override suspend fun getProductTransactionById(id: Int): ProductTransaction? {
        return productTransactionDao.getProductTransactionById(id)
    }

}