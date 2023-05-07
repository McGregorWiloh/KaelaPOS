package com.mcgregor.kaelapos.viewmodels

import androidx.lifecycle.ViewModel
import com.mcgregor.kaelapos.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val productRepository: ProductRepository
    ): ViewModel() {
}