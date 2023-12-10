package com.mcgregor.kaelapos.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mcgregor.kaelapos.models.Product
import com.mcgregor.kaelapos.models.Supplier
import com.mcgregor.kaelapos.navigation.KaelaScreens
import com.mcgregor.kaelapos.viewmodels.SupplierViewModel

@Composable
fun NewSupplierScreen(navController: NavController) {
    val viewModel: SupplierViewModel = hiltViewModel()
    val context = LocalContext.current
    val supplierName = remember { mutableStateOf("") }
    val supplierPhone = remember { mutableStateOf("") }
    val supplierAddress = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 48.dp, start = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row {

            TextField(
                value = supplierName.value,
                label = { Text(text = "Supplier Name") },
                onValueChange = {
                    supplierName.value = it
                })
        }

        Row {

            TextField(
                value = supplierPhone.value,
                label = { Text(text = "Phone Number") },
                onValueChange = {
                    supplierPhone.value = it
                })
        }

        Row {

            TextField(
                value = supplierAddress.value,
                label = { Text(text = "Supplier's Address") },
                onValueChange = {
                    supplierAddress.value = it
                })
        }

        Row(modifier = Modifier.padding(top = 32.dp), horizontalArrangement = Arrangement.Center) {
            Button(onClick = {
                if (supplierName.value.isNotEmpty() && supplierPhone.value.isNotEmpty() && supplierAddress.value.isNotEmpty()) {
                    val newSupplier = Supplier(supplierName.value, supplierPhone.value, supplierAddress.value)
                    viewModel.saveSupplierToDatabase(newSupplier)
                    navController.navigate(KaelaScreens.MainScreen.name) {
                        navController.popBackStack()

                    }
                } else {
                    Toast.makeText(context, "Please enter all supplier's information", Toast.LENGTH_SHORT)
                        .show()
                }

            }) {
                Text(text = "Save")
            }
        }

    }
}