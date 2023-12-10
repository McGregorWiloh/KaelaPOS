package com.mcgregor.kaelapos.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mcgregor.kaelapos.models.ProductTransaction
import com.mcgregor.kaelapos.screens.ItemListScreen
import com.mcgregor.kaelapos.screens.KaelaSplashScreen
import com.mcgregor.kaelapos.screens.MainScreen
import com.mcgregor.kaelapos.screens.NewItemScreen
import com.mcgregor.kaelapos.screens.NewSupplierScreen
import com.mcgregor.kaelapos.screens.ProductSelectionScreen
import com.mcgregor.kaelapos.screens.SalesScreen
import com.mcgregor.kaelapos.screens.SuppliersScreen

@Composable
fun KaelaNavigation() {
    val navController = rememberNavController()
    val selectedProductList = mutableListOf<ProductTransaction>()
    NavHost(
        navController = navController,
        startDestination = KaelaScreens.KaelaSplashScreen.name
    ) {
        composable(KaelaScreens.KaelaSplashScreen.name) {
            KaelaSplashScreen(navController = navController)
        }

        composable(KaelaScreens.MainScreen.name) {
            MainScreen(navController = navController)
        }

        composable(
            KaelaScreens.NewItemScreen.name) {
            NewItemScreen(navController = navController)
        }

        composable(KaelaScreens.ProductSelectionScreen.name) {
            ProductSelectionScreen(navController, selectedProductList)
        }

        composable(KaelaScreens.ItemListScreen.name) {
            ItemListScreen(navController = navController)
        }

        composable(KaelaScreens.SalesScreen.name) {
            SalesScreen(navController = navController)
        }

        composable(KaelaScreens.SuppliersScreen.name) {
            SuppliersScreen(navController = navController)
        }

        composable(KaelaScreens.NewSupplierScreen.name) {
            NewSupplierScreen(navController = navController)
        }

    }
}