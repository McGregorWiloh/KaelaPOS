package com.mcgregor.kaelapos.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun SuppliersScreen(navController: NavHostController) {

    val scaffoldState = rememberScaffoldState()
    val coroutineScope: CoroutineScope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { TopperAppBar(scaffoldState, coroutineScope) },
        drawerContent = { MyColumn() }
    ) {
        Text(text = "This works")
    }
}

@Composable
fun MyColumn() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "One")
        Text(text = "Two")
        Text(text = "Three")
        Text(text = "Four")
    }
}

@Composable
fun TopperAppBar(scaffoldState: ScaffoldState, coroutineScope: CoroutineScope) {
    val drawerState = scaffoldState.drawerState

    TopAppBar(elevation = 5.dp) {
        IconButton(onClick = {

            coroutineScope.launch {
                Log.d("TAG", "This works")
                if (drawerState.isClosed) {
                    drawerState.isOpen
                } else {
                    drawerState.isClosed
                }
            }
        }) {
            Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu icon")
        }
    }
}
