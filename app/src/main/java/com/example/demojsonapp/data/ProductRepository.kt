package com.example.demojsonapp.data


import com.example.demojsonapp.data.model.Product
import com.example.demojsonapp.data.model.ProductX
import com.example.demojsonapp.network.ProductApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

interface ProductRepository {
    suspend fun getProduct() : Product
    suspend fun getCertainProduct(productId : Int) : ProductX
}

class NetworkProductRepository(
    private val productApiService: ProductApiService
) : ProductRepository {

    override suspend fun getProduct(): Product {
        return withContext(Dispatchers.IO) {
            try {
                productApiService.getProducts()
            } catch (e: IOException) {
                throw Exception("Network error occurred: ${e.message}")
            } catch (e: Exception) {
                throw Exception("An unexpected error occurred: ${e.message}")
            }
        }
    }
    override suspend fun getCertainProduct(ProductId : Int): ProductX {
        return withContext(Dispatchers.IO) {
            try {
                productApiService.getCertainProduct(ProductId)
            } catch (e: IOException) {
                throw Exception("Network error occurred: ${e.message}")
            } catch (e: Exception) {
                throw Exception("An unexpected error occurred: ${e.message}")
            }
        }
    }
}