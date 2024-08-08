package com.example.demojsonapp.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.demojsonapp.ProductDetails
import com.example.demojsonapp.ProductList
import com.example.demojsonapp.R
import com.example.demojsonapp.data.model.Product
import com.example.demojsonapp.data.model.ProductX


@Composable
fun HomeScreen(
    navController: NavController,
    productUiState: ProductUiState,
    productViewModel : ProductViewModel,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
){
    when(productUiState){
        is ProductUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is ProductUiState.Success -> ProductListPage(productUiState.products, productViewModel,navController , modifier)
        is ProductUiState.Error -> ErrorScreen(retryAction, modifier = modifier.fillMaxSize())
    }

}

@Composable
fun ProductListPage(
    product : Product,
    productViewModel: ProductViewModel,
    navController: NavController,
    modifier: Modifier = Modifier
){
    Scaffold(
        topBar = {
                Text(
                    text = "Products List",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.displayMedium,
                    modifier = Modifier.padding(8.dp)
                    )
        }
    ) {it ->
        LazyColumn(contentPadding = it) {
            items(product.products){product->
                ProductCard(
                    product = product,
                    modifier
                        .clickable {
                            productViewModel.getProductDetails(product.id)
                            navController.navigate(ProductDetails){
                                popUpTo(ProductList){
                                    inclusive = true
                                }
                            }
                        }
                        .padding(8.dp)
                )
            }
        }
    }
}

@Composable
fun ProductCard(
    product: ProductX,
    modifier: Modifier = Modifier
){
    Card {
       Row(
           modifier = Modifier
               .fillMaxWidth()
               .padding(8.dp),
           verticalAlignment = Alignment.CenterVertically
       ) {
           AsyncImage(
               model = ImageRequest.Builder(context = LocalContext.current).data(product.images.firstOrNull())
                   .crossfade(true).build(),
               error = painterResource(R.drawable.ic_broken_image),
               placeholder = painterResource(R.drawable.loading_img),
               contentDescription = null,
               contentScale = ContentScale.Crop,
               modifier = Modifier
                   .size(100.dp)

           )
           Column(modifier = modifier.padding(start = 8.dp)) {
               Text(
                   text = product.title,
                   modifier = Modifier.padding(top = 8.dp),
                   fontSize = 20.sp,
                   fontWeight = FontWeight.Bold
               )
               Text(
                   text = product.description,
                   style = MaterialTheme.typography.bodySmall,
                   maxLines = 3,
                   overflow = TextOverflow.Ellipsis
               )
           }


       }
    }
}

@Composable
fun ErrorScreen(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error), contentDescription = ""
        )
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading)
    )
}



