package com.example.demojsonapp.Screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.demojsonapp.ProductApplication
import com.example.demojsonapp.data.ProductRepository
import com.example.demojsonapp.data.model.Product
import com.example.demojsonapp.data.model.ProductX
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException


sealed interface ProductUiState{
    data class Success(val products: Product) : ProductUiState
    object Error : ProductUiState
    object Loading : ProductUiState
}


sealed interface ProductDetailsUiState{
    data class Success(val product: ProductX) : ProductDetailsUiState
    object Error : ProductDetailsUiState
    object Loading : ProductDetailsUiState
}

class ProductViewModel(private val productRepository: ProductRepository): ViewModel(){
    var productUiState : ProductUiState by mutableStateOf(ProductUiState.Loading)
        private set

    var productDetailsUiState : ProductDetailsUiState by mutableStateOf(ProductDetailsUiState.Loading)
        private set

    fun getProduct() {
        viewModelScope.launch {
            productUiState = ProductUiState.Loading
            productUiState = try {
                ProductUiState.Success(productRepository.getProduct())
            }catch (e : IOException){
                ProductUiState.Error
            }
            catch (e : HttpException){
                ProductUiState.Error
            }
            productRepository.getProduct()
        }
    }

    fun getProductDetails(productId : Int){
        viewModelScope.launch {
            productDetailsUiState = ProductDetailsUiState.Loading
            productDetailsUiState = try {
                ProductDetailsUiState.Success(productRepository.getCertainProduct(productId))
            }catch (e : IOException){
                ProductDetailsUiState.Error
            }
            catch (e : HttpException){
                ProductDetailsUiState.Error
            }
        }
    }

    companion object{
        val Factory : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as ProductApplication)
                val productRepository = application.container.productRepository
                ProductViewModel(productRepository = productRepository)
            }
        }
    }
}