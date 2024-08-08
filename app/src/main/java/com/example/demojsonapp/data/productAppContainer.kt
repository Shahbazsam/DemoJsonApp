package com.example.demojsonapp.data

import com.example.demojsonapp.network.ProductApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.OkHttpClient.Builder
import okhttp3.logging.HttpLoggingInterceptor

import retrofit2.Retrofit


interface ProductAppContainer {

    val productRepository : ProductRepository
}

class DefaultContainer : ProductAppContainer {

    private val baseUrl = "https://dummyjson.com/"

    private val json = Json{
        ignoreUnknownKeys = true
    }


    private val interceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client: OkHttpClient = Builder().addInterceptor(interceptor).build()

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(client)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()

    private val retrofitService : ProductApiService by lazy {
        retrofit.create(ProductApiService::class.java)
    }

    override val productRepository: ProductRepository by lazy {
        NetworkProductRepository(retrofitService)

    }

}