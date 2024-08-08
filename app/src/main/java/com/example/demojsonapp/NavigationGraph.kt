package com.example.demojsonapp

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.demojsonapp.Screens.HomeScreen
import com.example.demojsonapp.Screens.MainPage
import com.example.demojsonapp.Screens.ProductDetailsScreen
import com.example.demojsonapp.Screens.ProductViewModel
import kotlinx.serialization.Serializable

@Composable
fun NavigationGraph(
    app : ProductApplication,
    viewModel: ProductViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory = ProductViewModel.Factory)
){
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Main
    ){
        composable<Main>{
            MainPage(
                navController = navController,
                productViewModel = viewModel
                )
        }
        composable<ProductList>{
            HomeScreen(
                productUiState = viewModel.productUiState,
                productViewModel = viewModel,
                retryAction = viewModel::getProduct,
                navController = navController
            )
        }
        composable<ProductDetails> {
            ProductDetailsScreen(
                productDetailsUiState = viewModel.productDetailsUiState,

            )
        }
    }
}

@Serializable
object Main
@Serializable
object ProductList
@Serializable
object ProductDetails