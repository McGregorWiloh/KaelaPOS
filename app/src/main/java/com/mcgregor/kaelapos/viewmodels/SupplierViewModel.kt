package com.mcgregor.kaelapos.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mcgregor.kaelapos.models.Product
import com.mcgregor.kaelapos.models.Supplier
import com.mcgregor.kaelapos.repository.KaelaPOSRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SupplierViewModel @Inject constructor(
    private val kaelaPOSRepository: KaelaPOSRepository, savedStateHandle: SavedStateHandle
): ViewModel() {
    val suppliers = kaelaPOSRepository.getAllSuppliers()

    fun saveSupplierToDatabase(supplier: Supplier){
        viewModelScope.launch {
            kaelaPOSRepository.insertSupplier(supplier) }
    }

    fun deleteSupplierFromDatabase(supplier: Supplier){
        viewModelScope.launch {
            kaelaPOSRepository.deleteSupplier(supplier)
        }
    }

    fun getProductFromDatabaseById(supplierId: Int): Supplier {
        var supplier = Supplier("", "", "")
        viewModelScope.launch {
            supplier = kaelaPOSRepository.getSupplierById(supplierId)!!
        }
        return supplier
    }

}