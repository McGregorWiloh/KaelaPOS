package com.mcgregor.kaelapos.widgets

import android.content.Context
import android.widget.Toast
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.mcgregor.kaelapos.navigation.KaelaScreens

//Floating action button for adding new transaction
@Composable
fun AddFb(navController: NavController, transaction: String, context: Context) {
    FloatingActionButton(onClick = {
        if (transaction == "create_supplier"){
            navController.navigate(KaelaScreens.NewSupplierScreen.name)
        }
        else {
            navController.navigate(KaelaScreens.ProductSelectionScreen.name)
        }
         }) {
        Icon(imageVector = Icons.Default.Add, contentDescription = "Add a new transaction")
    }
}
