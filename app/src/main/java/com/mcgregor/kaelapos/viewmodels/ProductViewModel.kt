package com.mcgregor.kaelapos.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mcgregor.kaelapos.models.Product
import com.mcgregor.kaelapos.repository.KaelaPOSRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val kaelaPOSRepository: KaelaPOSRepository, savedStateHandle: SavedStateHandle
) : ViewModel() {
    /*var product by mutableStateOf<Product?>(null)
        private set

    var productName by mutableStateOf("")
        private set

    var productPrice by mutableStateOf("")
        private set*/

    /*init {
        val productId = savedStateHandle.get<Int>("productId")!!
        if (productId != -1) {
            viewModelScope.launch {
                kaelaPOSRepository.getProductById(productId)?.let {
                    productName = it.productName
                    productPrice = it.productPrice
                    this@ProductViewModel.product = product
                }

            }
        }
    }*/

    val products = kaelaPOSRepository.getAllProducts()

    fun saveProductToDatabase(product: Product){
        viewModelScope.launch {
            kaelaPOSRepository.insertProduct(product) }
    }

    fun deleteProductFromDatabase(product: Product){
        viewModelScope.launch {
            kaelaPOSRepository.deleteProduct(product)
        }
    }

    fun getProductFromDatabaseById(productId: Int): Product {
        var product = Product("", "")
        viewModelScope.launch {
         product = kaelaPOSRepository.getProductById(productId)!!
        }
        return product
    }
}