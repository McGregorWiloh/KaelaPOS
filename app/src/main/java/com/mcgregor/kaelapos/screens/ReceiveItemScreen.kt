package com.mcgregor.kaelapos.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mcgregor.kaelapos.models.Product
import com.mcgregor.kaelapos.models.ProductTransaction
import com.mcgregor.kaelapos.models.Supplier
import com.mcgregor.kaelapos.navigation.KaelaScreens
import com.mcgregor.kaelapos.viewmodels.ProductTransactionViewModel
import com.mcgregor.kaelapos.viewmodels.ProductViewModel
import com.mcgregor.kaelapos.viewmodels.SupplierViewModel
import com.mcgregor.kaelapos.widgets.SearchBar

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ReceiveItemScreen(navController: NavHostController,
                      viewModel: ProductViewModel = hiltViewModel(),
                      viewModel2: SupplierViewModel = hiltViewModel(),
                      viewModel3: ProductTransactionViewModel = hiltViewModel()
) {
    val productsList = viewModel.products.collectAsState(initial = emptyList())
    val suppliersList = viewModel2.suppliers.collectAsState(initial = emptyList())
    val selectedProductList = remember {
        mutableStateListOf<ProductTransaction>()
    }
    val isVisible = rememberSaveable { mutableStateOf(true) }
    val show = rememberSaveable { mutableStateOf(true) }
    val selectedProduct = remember { mutableStateOf(Product("", "")) }
    val openDialog = remember { mutableStateOf(false) }
    val transactionDialog = remember { mutableStateOf(false) }
    val expanded = rememberSaveable { mutableStateOf(false) }
    val selectedSupplier = remember { mutableStateOf(Supplier("", "", "")) }
    val itemQuantity = remember { mutableStateOf("") }
    val totalBillAmount = remember { mutableStateOf(0.0) }
    val context = LocalContext.current
    val isDarkMode = isSystemInDarkTheme()
    val hideInvoice = rememberSaveable { mutableStateOf(true) }

    //get screen width
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val columnPortionSize = screenWidth / 4

    var searchText by remember {
        mutableStateOf("")
    }

    val textColor = when (isDarkMode) {
        true -> Color.White
        false -> Color.Black
    }

    val backgroundColor = when (isDarkMode) {
        true -> Color.Black
        false -> Color.White
    }

    val filteredList = if (searchText.isEmpty()) {
        mutableListOf<Product>()
    } else {
        productsList.value.toList().filter {
            it.productName.contains(searchText, ignoreCase = true)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = backgroundColor)
            .padding(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            SearchBar(searchText = searchText, onSearchTextChanged = {
                searchText = it
                hideInvoice.value = true
            })
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(5f)
        ) {
            LazyColumn {
                items(filteredList) { product ->
                    ClickableText(text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = 30.sp,
                                color = textColor
                            )
                        ) {
                            append(product.productName)
                        }
                    }, onClick = {
                        selectedProduct.value = product
                        openDialog.value = true

                    }, modifier = Modifier.padding(start = 16.dp))
                }
            }

            //if(selectedProductList.isNotEmpty() && !hideInvoice.value) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Description", modifier = Modifier.width(columnPortionSize + 30.dp))
                Text(text = "Quantity", modifier = Modifier.width(columnPortionSize - 10.dp))
                Text(text = "Unit Price", modifier = Modifier.width(columnPortionSize - 10.dp))
                Text(text = "Amount", modifier = Modifier.width(columnPortionSize - 10.dp))
            }

            Divider(modifier = Modifier.padding(top = 6.dp, bottom = 6.dp))

            LazyColumn {
                itemsIndexed(items = selectedProductList, key = null /*{ _, listItem ->
                    listItem.hashCode()
                }*/) { index, item ->
                    val state = rememberDismissState(
                        confirmStateChange = {
                            if (it == DismissValue.DismissedToStart) {
                                selectedProductList.remove(item)
                            }
                            true
                        }
                    )
                    SwipeToDismiss(state = state, background = {

                        val color = when (state.dismissDirection) {
                            DismissDirection.StartToEnd -> Color.Transparent
                            DismissDirection.EndToStart -> Color.Red
                            null -> Color.Magenta
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(color = color)
                                .padding(10.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete",
                                tint = Color.Black,
                                modifier = Modifier.align(Alignment.CenterEnd)
                            )
                        }
                    }, dismissContent = {
                        SampleItems(item, columnPortionSize)
                    },
                        directions = setOf(DismissDirection.EndToStart))
                    Divider()
                }



            }
            //LinearProgressIndicator(progress = 0.5f)
            Divider(modifier = Modifier.padding(top = 6.dp, bottom = 6.dp))
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1.6f)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.8f)
                    .padding(end = 20.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.Bottom
            ) {


                Text(
                    text = "Total Amount  M${
                        String.format(
                            "%.2f", calculateTotalAmount(
                                selectedProductList,
                                totalBillAmount
                            )
                        )
                    }", fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 20.dp)
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.8f),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = {
                    if (selectedProductList.isEmpty()) {
                        Toast.makeText(context, "You can't save an empty invoice bill!!", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        transactionDialog.value = true
                    } }) {
                    Text(text = "Save")
                }

                Button(onClick = {
                    selectedProductList.clear()
                    navController.popBackStack(KaelaScreens.MainScreen.name, false)
                }) {
                    Text(text = "Cancel")
                }
            }
        }


        //}
        // This is for picking the product quantity
        if (openDialog.value) {

            AlertDialog(
                onDismissRequest = {
                    // Dismiss the dialog when the user clicks outside the dialog or on the back
                    // button. If you want to disable that functionality, simply use an empty
                    // onCloseRequest.
                    openDialog.value = false
                },
                title = {
                    Text(
                        text = selectedProduct.value.productName,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                },
                text = {
                    Column {
                        TextField(
                            value = itemQuantity.value,
                            label = { Text(text = "Enter Quantity") },
                            onValueChange = {
                                if (it.isEmpty()) {
                                    itemQuantity.value = it
                                } else {
                                    itemQuantity.value = when (it.toDoubleOrNull()) {
                                        null -> itemQuantity.value
                                        else -> it
                                    }
                                }
                            },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = "Use the arrow below to select your supplier:")
                        IconButton(
                            onClick = { expanded.value = true },
                            modifier = Modifier.padding(bottom = 40.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = "Drop down icon"
                            )
                            suppliersList.value?.let { TopAppBarDropDownMenu2(it, expanded, context, selectedSupplier, isVisible, show) }
                        }
                        MyText2(selectedSupplier, isVisible.value)
                    }

                },

                confirmButton = {
                    Button(
                        onClick = {
                            openDialog.value = false

                            if (itemQuantity.value.isNotEmpty() && selectedProduct.value.productName.isNotEmpty()) {

                                createProductTransaction(
                                    selectedProduct.value,
                                    itemQuantity.value.toDouble(),
                                    selectedProductList,
                                    selectedSupplier.value
                                )
                            }
                            searchText = ""
                            itemQuantity.value = ""

                            Toast.makeText(
                                context,
                                "${selectedProductList.size} Items added to cart!!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }, modifier = Modifier.padding(start = 75.dp, end = 16.dp)
                    ) {
                        Text("Add Item")
                    }
                },
                dismissButton = {
                    Button(
                        onClick = {
                            openDialog.value = false
                            itemQuantity.value = ""
                            searchText = ""
                        }, colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
                    ) {
                        Text("Cancel")
                    }
                }
            )
        }

        if (transactionDialog.value) {

            AlertDialog(
                onDismissRequest = {
                    // Dismiss the dialog when the user clicks outside the dialog or on the back
                    // button. If you want to disable that functionality, simply use an empty
                    // onCloseRequest.
                    transactionDialog.value = false
                },
                title = {
                    Text(
                        text = "Confirm",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                },
                text = {
                    Text(text = "Are you sure you want to save this invoice?")

                },

                confirmButton = {
                    Button(
                        onClick = {
                            saveProductTransaction(selectedProductList, viewModel3)
                            transactionDialog.value = false
                            selectedProductList.clear() //clears the selected products after transaction
                            Toast.makeText(context, "Invoice saved successfully!!", Toast.LENGTH_SHORT).show()
                        }, modifier = Modifier.padding(start = 75.dp, end = 16.dp)
                    ) {
                        Text("Yes")
                    }
                },
                dismissButton = {
                    Button(
                        onClick = {
                            transactionDialog.value = false
                            selectedProductList.clear() //clears the selected products after transaction
                        }, colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
                    ) {
                        Text("No")
                    }
                },
                properties = DialogProperties(
                    dismissOnClickOutside = false,
                    dismissOnBackPress = false
                )
            )
        }
    }

}

fun saveProductTransaction(selectedProductList: SnapshotStateList<ProductTransaction>, viewModel: ProductTransactionViewModel) {
    for (productTransaction in selectedProductList) {
        viewModel.saveProductTransactionToDatabase(productTransaction)
    }
}

@Composable
fun TopAppBarDropDownMenu2(
    list: List<Supplier>,
    expanded: MutableState<Boolean>,
    context: Context,
    selectedSupplier: MutableState<Supplier>,
    isVisible: MutableState<Boolean>,
    show: MutableState<Boolean>
) {

    DropdownMenu(expanded = expanded.value, onDismissRequest = { expanded.value = false }) {
        for (i in list) {
            Text(text = i.supplierName!!, modifier = Modifier
                .clickable {
                    selectedSupplier.value = i
                    isVisible.value = true
                    show.value = false
                    expanded.value = false
                }
                .padding(start = 4.dp, end = 4.dp, bottom = 30.dp))
        }
    }
}

@Composable
fun MyText2(selectedSupplier: MutableState<Supplier>, isVisible: Boolean) {
    if (isVisible) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 40.dp), horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = selectedSupplier.value.supplierName,
                Modifier.padding(end = 10.dp),
                fontWeight = FontWeight.Bold
            )
        }

    }
}

