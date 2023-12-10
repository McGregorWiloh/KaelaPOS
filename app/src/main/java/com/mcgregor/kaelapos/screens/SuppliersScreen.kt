package com.mcgregor.kaelapos.screens

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mcgregor.kaelapos.models.Product
import com.mcgregor.kaelapos.models.Supplier
import com.mcgregor.kaelapos.viewmodels.SupplierViewModel
import com.mcgregor.kaelapos.widgets.AddFb
import com.mcgregor.kaelapos.widgets.AppBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SuppliersScreen(
    navController: NavHostController,
    viewModel: SupplierViewModel = hiltViewModel()
) {

    val suppliersList = viewModel.suppliers.collectAsState(initial = emptyList())
    val selectedSupplier = remember { mutableStateOf(Supplier("", "", "")) }
    val isDarkMode = isSystemInDarkTheme()
    val context = LocalContext.current

    val textColor = when (isDarkMode) {
        true -> Color.White
        false -> Color.Black
    }

    val backgroundColor = when (isDarkMode) {
        true -> Color.Black
        false -> Color.White
    }

    Scaffold(
        floatingActionButton = { AddFb(navController, "create_supplier", context) }
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = backgroundColor)
                .padding(4.dp)
        ) {
            LazyColumn {
                items(suppliersList.value) { supplier ->
                    Text(
                        text = supplier.supplierName,
                        color = textColor,
                        modifier = Modifier.clickable {
                            Toast.makeText(
                                context,
                                supplier.id.toString(),
                                Toast.LENGTH_SHORT
                            ).show()
                        })
                }
            }
        }

    }



}
