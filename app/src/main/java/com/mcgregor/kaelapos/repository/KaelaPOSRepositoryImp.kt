package com.mcgregor.kaelapos.repository

import com.mcgregor.kaelapos.dao.ProductDao
import com.mcgregor.kaelapos.models.Product
import kotlinx.coroutines.flow.Flow

class KaelaPOSRepositoryImp(
    private val productDao: ProductDao
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

}