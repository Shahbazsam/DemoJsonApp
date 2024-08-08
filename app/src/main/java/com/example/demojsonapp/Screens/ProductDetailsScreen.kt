package com.example.demojsonapp.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.demojsonapp.R
import com.example.demojsonapp.data.model.ProductX
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.HorizontalPager
@Composable
fun ProductDetailsScreen(
    productDetailsUiState: ProductDetailsUiState,
    modifier: Modifier = Modifier
){
    when(productDetailsUiState){
        is ProductDetailsUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is ProductDetailsUiState.Success -> SuccessScreen(productDetailsUiState.product, modifier)
        is ProductDetailsUiState.Error -> ProductDetailsErrorScreen( modifier = modifier.fillMaxSize())
    }
}

@Composable
fun SuccessScreen(product: ProductX, modifier: Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ProductDetailsCard(product = product)
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ProductDetailsCard(product: ProductX) {

    val pagerState = rememberPagerState(initialPage = 0)
    val count = product.images.size
    val coroutineScope = rememberCoroutineScope()
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp)
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            HorizontalPager(
                count = count,
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f) // Maintain aspect ratio
            ) { page ->
               if(page in product.images.indices){
                   AsyncImage(
                       model = ImageRequest.Builder(LocalContext.current)
                           .data(product.images[page])
                           .crossfade(true)
                           .build(),
                       placeholder = painterResource(id = R.drawable.ic_launcher_background),
                       error = painterResource(id = R.drawable.ic_connection_error),
                       contentDescription = "Product Image",
                       contentScale = ContentScale.Crop,
                       modifier = Modifier.fillMaxSize()
                   )
               }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = product.title,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Price: $${String.format("%.2f", product.price)}",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_star), // Replace with a star icon drawable
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = Color.Yellow
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = product.rating.toString(),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Brand: ${product.brand ?: "N/A"}",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Description : ${product.description}",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.fillMaxWidth()
            )

        }
    }
}


@Composable
fun ProductDetailsErrorScreen( modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error), contentDescription = ""
        )
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
        
    }
}