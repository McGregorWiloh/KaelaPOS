package com.mcgregor.kaelapos.screens

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.itextpdf.io.image.ImageDataFactory
import com.itextpdf.kernel.colors.ColorConstants
import com.itextpdf.kernel.geom.PageSize
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Cell
import com.itextpdf.layout.element.Image
import com.itextpdf.layout.element.LineSeparator
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.element.Tab
import com.itextpdf.layout.element.TabStop
import com.itextpdf.layout.element.Table
import com.itextpdf.layout.property.TabAlignment
import com.itextpdf.layout.property.TextAlignment
import com.itextpdf.layout.property.UnitValue
import com.mcgregor.kaelapos.R
import com.mcgregor.kaelapos.models.ProductTransaction
import com.mcgregor.kaelapos.navigation.KaelaScreens
import com.mcgregor.kaelapos.viewmodels.ProductViewModel
import com.mcgregor.kaelapos.widgets.AddFb
import com.mcgregor.kaelapos.widgets.AppBar
import com.mcgregor.kaelapos.widgets.SearchBar
import java.io.ByteArrayOutputStream
import java.io.File
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(
    navController: NavController,
    viewModel: ProductViewModel = hiltViewModel()
) {

    val productsList = viewModel.products.collectAsState(initial = emptyList())
    val context = LocalContext.current
    var searchText by remember {
        mutableStateOf("")
    }
    val filteredList = if (searchText.isEmpty()) {
        productsList.value.toList()
    } else {
        productsList.value.toList().filter {
            it.productName.contains(searchText, ignoreCase = true)
        }
    }

    Scaffold(
        topBar = { AppBar(navController) },
        floatingActionButton = { AddFb(navController, "first_transaction", context) }
    ) {

        MainScreenMenu(navController)

    }
}

@Composable
fun MainScreenMenu(navController: NavController) {
    Column(modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())) {
        val configuration = LocalConfiguration.current
        val screenWidth = configuration.screenWidthDp.dp
        val columnPortionSize = screenWidth / 2

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
        ) {
            Box(
                modifier = Modifier
                    .width(columnPortionSize)
                    .height(200.dp)
                    .padding(4.dp)

            ) {
                Card(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable(onClick = {
                            navController.navigate(KaelaScreens.SalesScreen.name)
                        }),
                    shape = RoundedCornerShape(14.dp),
                    backgroundColor = Color.Gray
                ) {

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.cash_register),
                            contentDescription = "Make a sale"
                        )
                        Text(text = "Make a sale", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    }

                }

            }
            Box(
                modifier = Modifier
                    .width(columnPortionSize)
                    .height(200.dp)
                    .padding(4.dp)


            ) {
                Card(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable(onClick = {
                            navController.navigate(KaelaScreens.SuppliersScreen.name)
                        }),
                    shape = RoundedCornerShape(14.dp),
                    backgroundColor = Color.Gray
                ) {

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.supplier),
                            contentDescription = "Supplier"
                        )
                        Text(text = "Suppliers", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    }

                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
        ) {
            Box(
                modifier = Modifier
                    .width(columnPortionSize)
                    .height(200.dp)
                    .padding(4.dp)

            ) {
                Card(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable(onClick = {
                            Log.d("TAG1", "This works")
                        }),
                    shape = RoundedCornerShape(14.dp),
                    backgroundColor = Color.Gray
                ) {

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.receive_items),
                            contentDescription = "Receive Items"
                        )
                        Text(text = "Receive Items", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    }

                }

            }
            Box(
                modifier = Modifier
                    .width(columnPortionSize)
                    .height(200.dp)
                    .padding(4.dp)

            ) {
                Card(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable(onClick = {
                            Log.d("TAG1", "This works")
                        }),
                    shape = RoundedCornerShape(14.dp),
                    backgroundColor = Color.Gray
                ) {

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.inventory),
                            contentDescription = "Inventory"
                        )
                        Text(text = "Inventory", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    }

                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
        ) {
            Box(
                modifier = Modifier
                    .width(columnPortionSize)
                    .height(200.dp)
                    .padding(4.dp)

            ) {
                Card(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable(onClick = {
                            Log.d("TAG1", "This works")
                        }),
                    shape = RoundedCornerShape(14.dp),
                    backgroundColor = Color.Gray
                ) {

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.customer_list),
                            contentDescription = "Customer List"
                        )
                        Text(text = "Customer List", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    }

                }

            }
            Box(
                modifier = Modifier
                    .width(columnPortionSize)
                    .height(200.dp)
                    .padding(4.dp)


            ) {
                Card(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable(onClick = {
                            Log.d("TAG1", "This works")
                        }),
                    shape = RoundedCornerShape(14.dp),
                    backgroundColor = Color.Gray
                ) {

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.employee_list),
                            contentDescription = "Employee List"
                        )
                        Text(text = "Employee List", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    }

                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
        ) {
            Box(
                modifier = Modifier
                    .width(columnPortionSize)
                    .height(200.dp)
                    .padding(4.dp)

            ) {
                Card(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable(onClick = {
                            Log.d("TAG1", "This works")
                        }),
                    shape = RoundedCornerShape(14.dp),
                    backgroundColor = Color.Gray
                ) {

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.reports),
                            contentDescription = "Reports"
                        )
                        Text(text = "Reports", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    }

                }

            }
            Box(
                modifier = Modifier
                    .width(columnPortionSize)
                    .height(200.dp)
                    .padding(4.dp)


            ) {
                Card(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable(onClick = {
                            Log.d("TAG1", "This works")
                        }),
                    shape = RoundedCornerShape(14.dp),
                    backgroundColor = Color.Gray
                ) {

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.preferences),
                            contentDescription = "Preferences"
                        )
                        Text(text = "Preferences", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    }

                }
            }
        }

    }
}



