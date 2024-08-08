package com.example.demojsonapp.network

import com.example.demojsonapp.data.model.Product
import com.example.demojsonapp.data.model.ProductX


import retrofit2.http.GET
import retrofit2.http.Path

interface ProductApiService {

    @GET("products")
    suspend fun getProducts(): Product

    @GET("products/{id}")
    suspend fun getCertainProduct(@Path("id") productId : Int):ProductX

}