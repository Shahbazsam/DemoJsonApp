package com.example.demojsonapp.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.demojsonapp.Main
import com.example.demojsonapp.ProductList

@Composable
fun MainPage(
    navController: NavController,
    productViewModel: ProductViewModel
){
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                productViewModel.getProduct()
                navController.navigate(ProductList){
                popUpTo(Main){
                    inclusive = true
                }
            } },
            modifier = Modifier
                .size(width = 280.dp , height = 90.dp)
                .background(Color.Black),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
        ) {
           Text(
               text = "View Products" ,
               color = Color.White,
               fontSize = 25.sp
               )
        }
    }
}

/*
@Preview(showBackground = true)
@Composable
fun previewFunction(

){

    val navController = rememberNavController()
    MainPage(navController = navController)
}*/
