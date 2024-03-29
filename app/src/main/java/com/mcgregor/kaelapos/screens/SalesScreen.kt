package com.mcgregor.kaelapos.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mcgregor.kaelapos.models.Product
import com.mcgregor.kaelapos.models.ProductTransaction
import com.mcgregor.kaelapos.models.Supplier
import com.mcgregor.kaelapos.viewmodels.ProductViewModel
import com.mcgregor.kaelapos.widgets.SearchBar

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SalesScreen(navController: NavHostController, viewModel: ProductViewModel = hiltViewModel()) {

    val productsList = viewModel.products.collectAsState(initial = emptyList())
    val selectedProductList = remember {
        mutableStateListOf<ProductTransaction>()
    }
    val selectedProduct = remember { mutableStateOf(Product("", "")) }
    val cashDialog = remember { mutableStateOf(false) }
    val openDialog = remember { mutableStateOf(false) }
    val transactionDialog = remember { mutableStateOf(false) }
    val cashOffered = remember { mutableStateOf("") }
    val itemQuantity = remember { mutableStateOf("") }
    val totalBillAmount = remember { mutableStateOf(0.0) }
    val change = remember { mutableStateOf("") }
    val list1 = remember {
        mutableStateListOf(ProductTransaction(Product("", ""), 0.0, 0.0))
    }
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
                
                
                /*items(selectedProductList) {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(bottom = 10.dp), horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = it.product.productName,
                            modifier = Modifier.width(columnPortionSize + 30.dp)
                        )
                        Text(
                            text = it.productQuantity.toString(),
                            modifier = Modifier.width(columnPortionSize - 10.dp)
                        )
                        Text(
                            text = "M${
                                String.format(
                                    "%.2f",
                                    it.product.productPrice.toDoubleOrNull()
                                )
                            }",
                            modifier = Modifier.width(columnPortionSize - 10.dp)
                        )
                        Text(
                            text = "M${String.format("%.2f", it.productTotalAmount)}",
                            modifier = Modifier.width(columnPortionSize - 10.dp)
                        )
                    }

                }*/
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
                Button(onClick = { cashDialog.value = true }) {
                    Text(text = "Cash")
                }
                
                Button(onClick = { /*TODO*/ }) {
                    Text(text = "Cancel")
                }
            }

            // TODO() //put 2/3/4 buttons here for print/cash/credit/cancel
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

                },

                confirmButton = {
                    Button(
                        onClick = {
                            openDialog.value = false

                            if (itemQuantity.value.isNotEmpty() && selectedProduct.value.productName.isNotEmpty()) {

                                createProductTransaction(
                                    selectedProduct.value,
                                    itemQuantity.value.toDouble(),
                                    selectedProductList
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

        //This is for entering the cash offered
        if (cashDialog.value) {

            AlertDialog(
                onDismissRequest = {
                    // Dismiss the dialog when the user clicks outside the dialog or on the back
                    // button. If you want to disable that functionality, simply use an empty
                    // onCloseRequest.
                    cashDialog.value = false
                },
                title = {
                    Text(
                        text = "Enter Amount",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                },
                text = {
                    TextField(
                        value = cashOffered.value,
                        onValueChange = {
                            if (it.isEmpty()) {
                                cashOffered.value = it
                            } else {
                                cashOffered.value = when (it.toDoubleOrNull()) {
                                    null -> cashOffered.value
                                    else -> it
                                }
                            }
                        },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )

                },

                confirmButton = {
                    Button(
                        onClick = {
                            cashDialog.value = false
                            if (cashOffered.value.isNotEmpty()) {
                                change.value = (cashOffered.value.toDouble() - totalBillAmount.value).toString()
                                Toast.makeText(
                                    context,
                                    "Transaction successful, your change is ${change.value}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            searchText = ""
                            cashOffered.value = ""
                            transactionDialog.value = true
                        }, modifier = Modifier.padding(start = 75.dp, end = 16.dp)
                    ) {
                        Text("Make Sale")
                    }
                },
                dismissButton = {
                    Button(
                        onClick = {
                            cashDialog.value = false
                            cashOffered.value = ""
                            searchText = ""
                        }, colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
                    ) {
                        Text("Cancel")
                    }
                },
                properties = DialogProperties(
                    dismissOnClickOutside = false,
                    dismissOnBackPress = false
                )
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
                        text = "Transaction Successful",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                },
                text = {
                    Text(text = "Your change is ${change.value}")

                },

                confirmButton = {
                    Button(
                        onClick = {
                            transactionDialog.value = false
                            selectedProductList.clear() //clears the selected products after transaction
                        }, modifier = Modifier.padding(start = 75.dp, end = 16.dp)
                    ) {
                        Text("Print")
                    }
                },
                dismissButton = {
                    Button(
                        onClick = {
                            transactionDialog.value = false
                            selectedProductList.clear() //clears the selected products after transaction
                        }, colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
                    ) {
                        Text("Cancel")
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



@Composable
fun SampleItems(item: ProductTransaction, columnPortionSize: Dp){
        Row(
            Modifier
                .fillMaxWidth()
                .height(30.dp)
                .background(MaterialTheme.colors.surface), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = item.product.productName,
                modifier = Modifier
                    .width(columnPortionSize + 30.dp)
                    .padding(bottom = 10.dp)
            )
            Text(
                text = item.productQuantity.toString(),
                modifier = Modifier
                    .width(columnPortionSize - 10.dp)
                    .padding(bottom = 10.dp)
            )
            Text(
                text = "M${
                    String.format(
                        "%.2f",
                        item.product.productPrice.toDoubleOrNull()
                    )
                }",
                modifier = Modifier
                    .width(columnPortionSize - 10.dp)
                    .padding(bottom = 10.dp)
            )
            Text(
                text = "M${String.format("%.2f", item.productTotalAmount)}",
                modifier = Modifier
                    .width(columnPortionSize - 10.dp)
                    .padding(bottom = 10.dp)
            )
        }
}


fun createProductTransaction(
    product: Product,
    itemQuantity: Double,
    selectedProductList: MutableList<ProductTransaction>,
    supplier: Supplier = Supplier("", "", "")
) {
    val productTotalAmount = product.productPrice.toDouble().times(itemQuantity)
    val productTransaction = ProductTransaction(product, itemQuantity, productTotalAmount, supplier)
    selectedProductList.add(productTransaction)
}

fun calculateTotalAmount(
    selectedProductList: MutableList<ProductTransaction>,
    totalBillAmount: MutableState<Double>
): Double {
    val totalAmountsList = mutableListOf<Double>()
    for (i in selectedProductList) {
        totalAmountsList.add(i.productTotalAmount)
    }
    totalBillAmount.value = totalAmountsList.sumOf { it }
    return totalBillAmount.value
}

