package com.example.demojsonapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val limit: Int,
    val products: List<ProductX>,
    val skip: Int,
    val total: Int
)