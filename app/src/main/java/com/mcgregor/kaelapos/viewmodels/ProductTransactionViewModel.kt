package com.mcgregor.kaelapos.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mcgregor.kaelapos.models.Product
import com.mcgregor.kaelapos.models.ProductTransaction
import com.mcgregor.kaelapos.models.Supplier
import com.mcgregor.kaelapos.repository.KaelaPOSRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductTransactionViewModel @Inject constructor(
    private val kaelaPOSRepository: KaelaPOSRepository, savedStateHandle: SavedStateHandle
): ViewModel() {
    val productTransactions = kaelaPOSRepository.getAllProductTransactions()

    fun saveProductTransactionToDatabase(productTransaction: ProductTransaction){
        viewModelScope.launch {
            kaelaPOSRepository.insertProductTransaction(productTransaction) }
    }

    fun deleteProductTransactionFromDatabase(productTransaction: ProductTransaction){
        viewModelScope.launch {
            kaelaPOSRepository.deleteProductTransaction(productTransaction)
        }
    }

    fun getProductTransactionFromDatabaseById(productTransactionId: Int): ProductTransaction {
        var productTransaction = ProductTransaction(Product("", ""), 0.0, 0.0, Supplier("", "", ""))
        viewModelScope.launch {
            productTransaction = kaelaPOSRepository.getProductTransactionById(productTransactionId)!!
        }
        return productTransaction
    }
}