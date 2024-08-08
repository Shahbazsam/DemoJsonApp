package com.example.demojsonapp.data.model

import kotlinx.serialization.Serializable


@Serializable
data class Meta(
    val barcode: String,
    val createdAt: String,
    val qrCode: String,
    val updatedAt: String
)